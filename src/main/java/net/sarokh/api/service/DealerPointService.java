package net.sarokh.api.service;

import net.sarokh.api.dao.DealerPointRepository;
import net.sarokh.api.dao.UserRepository;
import net.sarokh.api.model.DealerPointDTO;
import net.sarokh.api.model.entity.*;
import net.sarokh.api.model.enums.UserRolesEnum;
import net.sarokh.api.model.enums.WalletTypeEnum;
import net.sarokh.api.model.trip.TripPointsDTO;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DealerPointService {

    @Autowired
    private DealerPointRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;

    public ApiResponse add(DealerPointDTO pointDTO){

        DealerPoint point = createDealerPointObject(pointDTO);

        createDealerPointWallets(point);

        return ApiResponse.builder()
                .data(repository.save(point))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DEALER_POINT_SUCCESSFULLY_CREATED)
                .build();
    }

    private void createDealerPointWallets(DealerPoint dealer){
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(walletService.createWalletObject("Point Collection Wallet", WalletTypeEnum.DealerCollection.getWalletType(),
                dealer.getId(), UserRolesEnum.DealerPoint.name(), dealer.getUserId(), dealer.getDealerPointName()));
        wallets.add(walletService.createWalletObject("Point Compensation Wallet", WalletTypeEnum.DealerCompensation.getWalletType(),
                dealer.getId(), UserRolesEnum.DealerPoint.name(), dealer.getUserId(), dealer.getDealerPointName()));
        walletService.addWallets(wallets);
    }

    public ApiResponse getDealerPointById(Integer id){
        return ApiResponse.builder()
                .data(repository.findById(id))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDealerPointByName(String pointName){
        return ApiResponse.builder()
                .data(repository.findByDealerPointName(pointName))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public DealerPoint getDealerPointByUserId(Integer userId){
        Optional<DealerPoint> dealerPoint = repository.findByUserId(userId);
        return dealerPoint.isPresent() ? dealerPoint.get() : null;
    }

    public ApiResponse getDealerPointsList() {
        return ApiResponse.builder()
                .data(repository.findAll())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse updateDealerPoint(DealerPointDTO pointDTO){

        DealerPoint point = createDealerPointObject(pointDTO);

        return ApiResponse.builder()
                .data(point)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.DEALER_POINT_SUCCESSFULLY_CREATED)
                .build();
    }

    public ApiResponse deleteDealerPoint(Integer id) {
        repository.deleteById(id);
        return ApiResponse.builder()
                .data(id + "successfully deleted")
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public List<TripPointsDTO> getDealerPointCollectionWallets() {
        Iterable<?> pointsList =  repository.findAllDealerPointsCollectionWallets();
        Iterator iterator = pointsList.iterator();
        List<TripPointsDTO> tripPointsList = new ArrayList<>();
        while (iterator.hasNext()) {
            Object[] point = (Object[]) iterator.next();
            tripPointsList.add(TripPointsDTO.builder()
                    .pointId(point[0] != null ? Integer.parseInt(point[0].toString()) : 0)
                    .dealerPointName(point[1] != null ? point[1].toString() : null)
                    .walletBalance(point[2] != null ? point[2].toString() : null)
                    .address(point[3] != null ? point[3].toString() : null)
                    .build());

        }

        return tripPointsList;
    }

    private DealerPoint createDealerPointObject(DealerPointDTO pointDTO){
        DealerPoint point = new DealerPoint();
        point.setDealerPointName(pointDTO.getDealerPointName());
        point.setOwner(pointDTO.getOwner());
        point.setCity(pointDTO.getCity());
        point.setAddress(pointDTO.getAddress());
        point.setCountry(pointDTO.getCountry());
        point.setPostalCode(pointDTO.getPostalCode());
        point.setStatus("Active");
        point.setDealerId(pointDTO.getOwnerId());
        point.setLocationLatitude(pointDTO.getLocationLatitude());
        point.setLocationLongitude(pointDTO.getLocationLongitude());
        point.setCommercialRegistrationFile(pointDTO.getCommercialRegistrationFile());
        point.setCommercialRegistrationNumber(pointDTO.getCommercialRegistrationNumber());
        point.setPointPicture(pointDTO.getPointPicture());
        point.setOperatorContact(pointDTO.getOperatorContact());
        point.setOperatorName(pointDTO.getOperatorName());

        User user = new User();
        user.setFullName(pointDTO.getDealerPointName());
        user.setUserName(pointDTO.getUserName());
        user.setUserPassword(pointDTO.getPassword());
        user.setContact(pointDTO.getOperatorContact());
        user.setDob(new Date().toString());
        user.setProfilePicture(point.getPointPicture());
        user.setDesignation(UserRolesEnum.DealerPoint.toString());
        user.setUserType(UserRolesEnum.DealerPoint.toString());
        Optional<Role> dealerRole = roleService.getRoleByName(UserRolesEnum.DealerPoint.toString());

        if (dealerRole.isPresent()){
            user.setRole(dealerRole.get());
            user.setRoleId(dealerRole.get().getId());
        }

        if (pointDTO.getId() != null && pointDTO.getId() > 0){
            point.setId(pointDTO.getId());
            user.setUserId(pointDTO.getUserId());
            point.setUserId(pointDTO.getUserId());
            userRepository.save(user);
        } else {
            userRepository.save(user);
            point.setUserId(user.getUserId());
        }

        repository.save(point);

        return point;
    }

}
