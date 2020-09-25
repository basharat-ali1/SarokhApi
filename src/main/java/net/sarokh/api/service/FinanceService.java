package net.sarokh.api.service;


import net.sarokh.api.dao.LedgerRepository;
import net.sarokh.api.model.admin.*;
import net.sarokh.api.model.entity.Ledger;
import net.sarokh.api.model.entity.PaymentTransaction;
import net.sarokh.api.model.enums.BillCategoryEnum;
import net.sarokh.api.model.enums.TransactionTypeEnum;
import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private PaymentTransactionService paymentTransactionService;

    @Autowired
    private WalletService walletService;

    public ApiResponse getFinanceDashboard(){

        return ApiResponse.builder()
                .data(FinanceDashboardDTO.builder()
                        .currentBalance(255)
                        .dueAmount(23)
                        .monthlyCodCollection(600)
                        .monthlyPayable(200)
                        .monthlyReceivable(150)
                        .totalExpense(789)
                        .walletList(walletService.getWalletsList())
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse allLedgers(){
        List<AllLedgerDTO> list = new ArrayList<>();
        Iterable<Ledger> ledgers = ledgerRepository.findAll();
        /*
        Iterator<Ledger> iterator = ledgers.iterator();

        while (iterator.hasNext()) {
            Ledger ledger = iterator.next();
            list.add(AllLedgerDTO.builder()
                    .currentBalance(0)
                    .ledgerId(ledger.getId())
                    .ledgerHolder("Sarokh")
                    .ledgerName(ledger.getLedgerName())
                    .payable(0)
                    .startDate(ledger.getStartDate())
                    .endDate(ledger.getEndDate())
                    .receivable(ledger.getTotalAmount())
                    .build());
        }*/

        return ApiResponse.builder()
                .data(ledgers)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getLedgerDetails(Integer id){

        return ApiResponse.builder()
                .data(FinanceLedgerDTO.builder()
                        .dateTime("25-03-2020 10:00")
                        .transactionId(id)
                        .transactionType("Credit")
                        .from("Driver")
                        .to("Sarokh")
                        .amount(968)
                        .paymentMethod("Cash")
                        .description("Paid by driver")
                        .billUpload("bill.png")
                        .build())
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }


    public ApiResponse receiveCashByCashier(PaymentTransaction paymentTransaction){
        paymentTransaction.setTransactionType(TransactionTypeEnum.Credit.name());

        return ApiResponse.builder()
                .data(paymentTransactionService.addPaymentTransaction(paymentTransaction))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.AMOUNT_RECEIVED)
                .build();
    }

    public ApiResponse dispensingCash(PaymentTransaction paymentTransaction){
        paymentTransaction.setTransactionType(TransactionTypeEnum.Debit.name());

        return ApiResponse.builder()
                .data(paymentTransactionService.addPaymentTransaction(paymentTransaction))
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.AMOUNT_DIPENSED)
                .build();
    }

    public ApiResponse getCODCollection(){
        List<CODCollectionDTO> list = new ArrayList<>();

     /*   Iterable<Ledger> codLedgers = ledgerRepository.findAllByLedgerName(BillCategoryEnum.ShipperCod.getBillCateogry());

        Iterator<Ledger> iterator = codLedgers.iterator();

        while (iterator.hasNext()) {
            Ledger ledger = iterator.next();
            list.add(CODCollectionDTO.builder()
                    .ledgerId(ledger.getId())
                    .ledgerName(ledger.getLedgerName())
                    .currentBalance(236)
                    .receivable(698)
                    .payable(784)
                    .billView(null)
                    .ledgerHolder("Driver")
                    .holderType("Driver")
                    .notes("This is note")
                    .build());
        }*/
        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getShipperBilling(){
        List<ShipperBillingDTO> list = new ArrayList<>();
        //Iterable<Ledger> shipperBilling = ledgerRepository.getShipperBilling();

        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDriverPayout(){
       /* List<DriverPayoutDTO> list = new ArrayList<>();
        list.add(DriverPayoutDTO.builder()
                .ledgerId(23)
                .driverId(5)
                .timeDate("20-03-2020 11:30")
                .ledgerName("COD")
                .transactionType("Debit")
                .driverType("Freelancer")
                .receivable(20)
                .payable(32)
                .billView(null)
                .paymentMethod("Cash")
                .description("ledger description")
                .notes("notes of ledger")
                .build());*/

        //Iterable<Ledger> driverPayouts = ledgerRepository.findAllByLedgerName(BillCategoryEnum.DriverPayout.getBillCateogry());
        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

    public ApiResponse getDealerPayout(){
       /* List<DealerPayoutDTO> list = new ArrayList<>();
        list.add(DealerPayoutDTO.builder()
                .ledgerId(10)
                .timeDate("25-03-2020 09:30")
                .ledgerName("COD")
                .transactionType("Debit")
                .receivable(200)
                .payable(532)
                .billView(null)
                .paymentMethod("Cash")
                .description("ledger description")
                .notes("notes of ledger")
                .build());*/
        //Iterable<Ledger> dealerPayouts = ledgerRepository.findAllByLedgerName(BillCategoryEnum.DealerPayout.getBillCateogry());
        return ApiResponse.builder()
                .data(null)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.SUCCESS)
                .build();
    }

}
