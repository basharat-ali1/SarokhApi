package net.sarokh.api.service;

import net.sarokh.api.dao.*;
import net.sarokh.api.model.*;
import net.sarokh.api.model.admin.AllShipperBillingDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.BillCategoryEnum;
import net.sarokh.api.model.enums.BillTypeEnum;
import net.sarokh.api.model.enums.PaymentStatusEnum;
import net.sarokh.api.model.enums.TransactionTypeEnum;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository repository;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private ShipmentOrderItemRepository orderItemRepository;

    /*public ApiResponse addLedger(Ledger ledger){
        if (ledger.getShipperId() != null || (ledger.getLedgerFor() != null && ledger.getLedgerFor().equals("Shipper"))){
            Integer shipperId = ledger.getShipperId();
            ledger.setBillCategory(BillCategoryEnum.ShipperCod.getBillCateogry());

            if (ledger.getLedgerFor() != null && ledger.getLedgerForDetail() != null){
                shipperId = ledger.getLedgerForDetail();
                ledger.setBillCategory(BillCategoryEnum.ShipperDeliveryCharges.getBillCateogry());
            }

            Optional<Shipper> shipper = shipperRepository.findById(shipperId);
            if (shipper.isPresent()){
                ledger.setPayeeDetail(shipper.get().getFirstName() + " " + shipper.get().getLastName());
            }
        } else if (ledger.getLedgerFor() != null && ledger.getLedgerFor().equals("Driver")){
            Integer driverId = ledger.getLedgerForDetail();
            Optional<Driver> driver = driverRepository.findById(driverId);
            if (driver.isPresent()){
                ledger.setPayeeDetail(driver.get().getFirstName() + " " + driver.get().getLastName());
                ledger.setBillCategory(BillCategoryEnum.DriverPayout.getBillCateogry());
            }
        } else if (ledger.getLedgerFor() != null && ledger.getLedgerFor().equals("Dealer")){
            Integer dealerId = ledger.getLedgerForDetail();
            Optional<Dealer> dealer = dealerRepository.findById(dealerId);
            if (dealer.isPresent()){
                ledger.setPayeeDetail(dealer.get().getCompanyName());
                ledger.setBillCategory(BillCategoryEnum.DealerPayout.getBillCateogry());
            }
        }

        double totalAmount = updateOrderLedgerStatus(ledger.getShipmentsIdList());

        ledger.setTotalAmount(totalAmount);
        ledger.setPaymentStatus(PaymentStatusEnum.UnPaid.name());
        ledger.setTransactionType(TransactionTypeEnum.Debit.name());
        repository.save(ledger);

        return ApiResponse.builder()
                .data(ledger)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }*/

    public ApiResponse addLedger(Ledger ledger){
        if (ledger.getShipperId() != null || (ledger.getLedgerFor() != null && ledger.getLedgerFor().equals("Shipper"))){
            Integer shipperId = ledger.getShipperId();

            if (ledger.getLedgerFor() != null && ledger.getLedgerForDetail() != null){
                shipperId = ledger.getLedgerForDetail();
            }

            Optional<Shipper> shipper = shipperRepository.findById(shipperId);
            if (shipper.isPresent()){
                ledger.setPayeeDetail(shipper.get().getFirstName() + " " + shipper.get().getLastName());
            }
        } else if (ledger.getLedgerFor() != null && ledger.getLedgerFor().equals("Driver")){
            Integer driverId = ledger.getLedgerForDetail();
            Optional<Driver> driver = driverRepository.findById(driverId);
            if (driver.isPresent()){
                ledger.setPayeeDetail(driver.get().getFirstName() + " " + driver.get().getLastName());
            }
        } else if (ledger.getLedgerFor() != null && ledger.getLedgerFor().equals("Dealer")){
            Integer dealerId = ledger.getLedgerForDetail();
            Optional<Dealer> dealer = dealerRepository.findById(dealerId);
            if (dealer.isPresent()){
                ledger.setPayeeDetail(dealer.get().getCompanyName());
            }
        }

        if (!ledger.getBillCategory().equals(BillCategoryEnum.Other.getBillCateogry())) {
            double totalAmount = updateOrderLedgerStatus(ledger.getShipmentsIdList());
            ledger.setTotalAmount(totalAmount);
        }

        ledger.setPaymentStatus(PaymentStatusEnum.UnPaid.name());
        ledger.setTransactionType(TransactionTypeEnum.Debit.name());
        repository.save(ledger);

        return ApiResponse.builder()
                .data(ledger)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getLedgerById(Integer id){
        Optional<Ledger> bill = repository.findById(id);
        List<BillSummaryDTO> billSummaryItems = new ArrayList<>();

        if (bill.isPresent()){
            String[] shipmentsList = bill.get().getShipmentsIdList().split(",");
            for (String shipmentId : shipmentsList){
                Optional<ShipmentOrderItem> shipmentOrder = orderItemRepository.findByTrackingNumber(shipmentId);
                if (shipmentOrder.isPresent()){
                    billSummaryItems.add(BillSummaryDTO.builder()
                            .trackingNumber(shipmentOrder.get().getTrackingNumber())
                            .units(1)
                            .amount(shipmentOrder.get().getShipmentBilledAmount())
                            .date(bill.get().getCreatedDatetime().toString())
                            .build());
                }
            }

            BillDetailDTO detailDTO = BillDetailDTO.builder()
                    .id(bill.get().getId())
                    .billType(bill.get().getBillType())
                    .billCategory(bill.get().getBillCategory())
                    .userType(bill.get().getUserType())
                    .billTo(bill.get().getPayeeDetail())
                    .creationDate(bill.get().getCreatedDatetime().toString())
                    .startingDate(bill.get().getStartDate())
                    .endingDate(bill.get().getEndDate())
                    .dueDate(bill.get().getDueDate())
                    .walletName(bill.get().getPayeeWallet())
                    .status(bill.get().getPaymentStatus())
                    .billSummary(billSummaryItems)
                    .build();

            return ApiResponse.builder()
                    .data(detailDTO)
                    .message(ApplicationMessagesUtil.SUCCESS)
                    .status(HttpStatus.OK.value())
                    .build();
        }

        return ApiResponse.builder()
                .data(bill)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getLedgersList() {
        return ApiResponse.builder()
                .data(repository.findAllLedgersDescOrder())
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getShipperBilling() {
        Iterable<?> ledgers = repository.findShipperBilling();
        Iterator<?> iterator = ledgers.iterator();
        List<AllShipperBillingDTO> billingList = new ArrayList<>();
        while(iterator.hasNext()){
            Object[] bill = (Object[]) iterator.next();
            billingList.add( AllShipperBillingDTO.builder()
                    .id(bill[0])
                    .shipperName(bill[1] + " " + bill[2])
                    .transactionType(bill[3])
                    .date(bill[4])
                    .amount(bill[5])
                    .numberOfShipments(bill[6] != null ? bill[6].toString().split(",").length : 0)
                    .build());
        }

        return ApiResponse.builder()
                .data(billingList)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getShipperLedgersList(Integer shipperId) {
        return ApiResponse.builder()
                .data(repository.findAllByShipperId(shipperId))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse updateLedger(Ledger ledger){
        return ApiResponse.builder()
                .data(repository.save(ledger))
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse deleteLedger(Integer id){
        repository.deleteById(id);
        return ApiResponse.builder()
                .data("Bill successfully deleted.")
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getUnPaidLedgers(){
        Iterable<Ledger> ledgers = repository.findAllByPaymentStatus(PaymentStatusEnum.UnPaid.name());
        return ApiResponse.builder()
                .data(ledgers)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getUserUnpaidBills(GetBillDetailDTO billToDetailDTO){
        Iterable<Ledger> ledgers = repository.findAllByPaymentStatusAndBillToAndUserType(PaymentStatusEnum.UnPaid.name(), billToDetailDTO.getUserId(), billToDetailDTO.getUserType());
        return ApiResponse.builder()
                .data(ledgers)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse recordBillPayment(RecordPaymentDTO recordPaymentDTO){
        //Optional<Ledger>
        //Iterable<Ledger> ledgers = repository.findAllByPaymentStatusAndBillTo(PaymentStatusEnum.UnPaid.name(), billTo);
        return ApiResponse.builder()
                .data(null)
                .message(ApplicationMessagesUtil.BILL_PAID_SUCCESSFULLY)
                .status(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse getLedgerForDetails(String ledgerFor){
        List<LedgerForDTO> list = new ArrayList<>();

        if (ApplicationUtil.isNotNullAndEmpty(ledgerFor)) {
            if (ledgerFor.equals("Driver")) {
                Iterable<Driver> drivers = driverRepository.findAll();
                drivers.forEach(driver -> {
                            list.add(LedgerForDTO.builder()
                                    .id(driver.getId())
                                    .name(driver.getFirstName() + " " + driver.getLastName())
                                    .build());
                        }
                );
            } else if (ledgerFor.equals("Dealer")) {
                Iterable<Dealer> dealers = dealerRepository.findAll();
                dealers.forEach(dealer -> {
                    list.add(LedgerForDTO.builder()
                            .id(dealer.getId())
                            .name(dealer.getCompanyName())
                            .build());
                    }
                );
            } else if (ledgerFor.equals("Shipper")) {
                Iterable<Shipper> shippers = shipperRepository.findAll();
                shippers.forEach(shipper -> {
                            list.add(LedgerForDTO.builder()
                                    .id(shipper.getId())
                                    .name(shipper.getFirstName() + " " + shipper.getLastName())
                                    .build());
                        }
                );
            }

        }

        return ApiResponse.builder()
                .data(list)
                .message(ApplicationMessagesUtil.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    private Double updateOrderLedgerStatus(String shipmentIds){
        double totalAmount = 0;
        if (shipmentIds != null){

            String[] shipments = shipmentIds.split(",");
            for (int i=0;i<shipments.length;i++) {
                Optional<ShipmentOrderItem> shipment = orderItemRepository.findById(Integer.parseInt(shipments[i]));
                if (shipment.isPresent()){
                    totalAmount += shipment.get().getCodAmount();
                    shipment.get().setLedgerCreated(1);
                    orderItemRepository.save(shipment.get());
                }
            }
        }

        return totalAmount;
    }
    
}
