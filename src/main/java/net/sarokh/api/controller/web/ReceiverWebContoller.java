package net.sarokh.api.controller.web;

import net.sarokh.api.dao.DealerPointRepository;
import net.sarokh.api.dao.OrderRepository;
import net.sarokh.api.dao.ShipperRepository;
import net.sarokh.api.model.entity.DealerPoint;
import net.sarokh.api.model.entity.ShipmentOrder;
import net.sarokh.api.model.entity.Shipper;
import net.sarokh.api.model.enums.DeliveryTypeEnum;
import net.sarokh.api.model.enums.OrderDeliveryStatusEnum;
import net.sarokh.api.model.enums.PickupTypeEnum;
import net.sarokh.api.model.web.ReceiverOrderFormDTO;
import net.sarokh.api.model.web.TrackOrderFormDTO;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.ApplicationUtil;
import net.sarokh.api.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ReceiverWebContoller {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private DealerPointRepository dealerPointRepository;

    private final String JEDDAH_LATITUDE = "";
    private final String JEDDAH_LONGITUDE = "";

    @RequestMapping(value = "/trackorder", method = RequestMethod.GET)
    public String loadTrackOrderForm(Model model){
        model.addAttribute("trackingform", new TrackOrderFormDTO());
        return "trackorder";
    }

    @RequestMapping(value = "/searchorder", method = RequestMethod.POST)
    public String searchOrder(@ModelAttribute TrackOrderFormDTO form, Model model) {

        System.out.println(form.getTrackingNumber() + "-" + form.getOtp());

        if (null != form && null != form.getTrackingNumber()) {

            if (null == form.getOtp() || (null != form.getOtp() && form.getOtp() != 1111)){
                model.addAttribute("trackingform", new TrackOrderFormDTO());
                model.addAttribute("error" , ApplicationMessagesUtil.INVALID_OTP_NUMBER);

                return "trackorder";
            }

            Optional<ShipmentOrder> order = orderRepository.findByOrderId(form.getTrackingNumber());

            if (order.isPresent()){

                if (order.get().getStatus().equals(OrderDeliveryStatusEnum.Delivered.name())){
                    model.addAttribute("error" , ApplicationMessagesUtil.DELIVERED_ORDER);
                } else if (order.get().getStatus().equals(OrderDeliveryStatusEnum.Stolen.name())){
                    model.addAttribute("error" , ApplicationMessagesUtil.STOLEN_OR_MISSING);
                } else {
                    ReceiverOrderFormDTO orderForm = new ReceiverOrderFormDTO();
                    orderForm.setTrackingNumber(form.getTrackingNumber());
                    orderForm.setDeliveryLocation("Last Mile");
                    model.addAttribute("receiveform", orderForm);
                    model.addAttribute("order", order.get());
                    model.addAttribute("orderItem", order.get().getShipmentOrderItems().get(0));

                    Optional<Shipper> shipper = shipperRepository.findById(order.get().getShipperId());
                    if (shipper.isPresent()){
                        model.addAttribute("shipperName", shipper.get().getFirstName() + " " + shipper.get().getLastName());
                    }

                    if (!order.get().getDeliveryLocation().equals(DeliveryTypeEnum.PredefinedLocation.getDeliveryTypeName())){
                        model.addAttribute("showbuttons" , false);
                    } else {
                        model.addAttribute("showbuttons" , true);
                    }

                    return "orderdetail";
                }
            } else {
                model.addAttribute("error" , ApplicationMessagesUtil.INVALID_TRACKING_NUMBER);
            }
        }

        model.addAttribute("trackingform", new TrackOrderFormDTO());

        return "trackorder";
    }

    @RequestMapping(value = "/receiveorder", method = RequestMethod.POST)
    public String receiveOrder(@ModelAttribute ReceiverOrderFormDTO form, Model model) {

        System.out.println(form.getTrackingNumber() + "-" + form.getDeliveryLocation() );

        if (form != null && form.getDeliveryLocation() != null && form.getDeliveryLocation().equals(PickupTypeEnum.DealerPoint.getPickupTypeName())){
            List<DealerPoint> dealerPoints = dealerPointRepository.findAll();
            model.addAttribute("dealerPoints", dealerPoints);
        }

        model.addAttribute("deliveryLocation", form.getDeliveryLocation());
        model.addAttribute("trackingform", form);

        return "addressmap";
    }

    @RequestMapping(value = "/updatedelivery", method = RequestMethod.POST)
    public String updateDeliveryLocation(@ModelAttribute ReceiverOrderFormDTO form, Model model) {

        System.out.println(form.getTrackingNumber() + "-" + form.getDeliveryLocation() );

        if (ApplicationUtil.isNotNullAndEmpty(form.getTrackingNumber())){
            Optional<ShipmentOrder> order = orderRepository.findByOrderId(form.getTrackingNumber());
            if (order.isPresent()) {
                if (form.getDeliveryLocation().equals(DeliveryTypeEnum.LastMile.getDeliveryTypeName())) {
                    order.get().setDeliveryLocation(DeliveryTypeEnum.LastMile.getDeliveryTypeName());
                    order.get().getShipmentOrderItems().get(0).setAddress(form.getAddress());
                    order.get().getShipmentOrderItems().get(0).setDeliveryDate(DateUtil.toDateFromString(form.getDate()));
                } else {
                    order.get().setDeliveryLocation(PickupTypeEnum.DealerPoint.getPickupTypeName());
                    order.get().setDeliveryLocationId(form.getDealerPointId());
                    Optional<DealerPoint> point = dealerPointRepository.findById(form.getDealerPointId());
                    order.get().setDeliveryLocationDetail(point.get().getDealerPointName());
                    order.get().getShipmentOrderItems().get(0).setDeliveryDate(DateUtil.toDateFromString(form.getDate()));
                }
                orderRepository.save(order.get());

                model.addAttribute("message" , ApplicationMessagesUtil.ORDER_RECEIVER_UPDATED);
                model.addAttribute("trackingform", new TrackOrderFormDTO());
                return "trackorder";
            }
        }

        model.addAttribute("trackingform", new TrackOrderFormDTO());
        model.addAttribute("error" , ApplicationMessagesUtil.INVALID_ORDER_INFO);

        return "trackorder";
    }

 /*   @RequestMapping(value = "/trackorder1")
    public String index(Model model) {
        model.addAttribute("lat", "21.3891");
        model.addAttribute("long", "39.8579");

        return "addressmap";
    }
*/

}
