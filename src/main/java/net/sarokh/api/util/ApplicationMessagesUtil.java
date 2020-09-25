package net.sarokh.api.util;

public interface ApplicationMessagesUtil {

    String SUCCESS = "Success";

    String ORDER_SUCCESSFULLY_CREATED = "New shipment successfully created.";
    String ORDER_SUCCESSFULLY_UPDATED = "Shipment successfully updated.";
    String CASH_HANDED_OVER_SUCCESSFULLY = "Cash handed over successfully.";
    String SHIPMENT_HANDED_OVER_SUCCESSFULLY = "Successfully delivered to receiver.";
    String SHIPMENT_RECEIVED_SUCCESSFULLY = "Shipment received successfully.";
    String SHIPPER_SUCCESSFULLY_CREATED = "New shipper successfully created.";
    String DEALER_SUCCESSFULLY_CREATED = "New dealer successfully created.";
    String DEALER_POINT_SUCCESSFULLY_CREATED = "New dealer point successfully created.";
    String CITY_SUCCESSFULLY_CREATED = "New city successfully created.";
    String ZONE_SUCCESSFULLY_CREATED = "New zone successfully created.";
    String DRIVER_SUCCESSFULLY_CREATED = "New driver successfully created.";
    String DRIVER_EXPENSE_SUCCESSFULLY_CREATED = "New driver expense successfully created.";
    String VEHICLE_SUCCESSFULLY_CREATED = "New vehicle successfully created.";
    String PASSWORD_SUCCESSFULLY_CHANGED = "Password changed successfully.";
    String FILE_UPLOAD_SUCCESSFULLY = "File uploaded successfully.";
    String ORDER_RECEIVER_UPDATED = "Receiver location successfully updated.";
    String VENDOR_SUCCESSFULLY_CREATED = "New vendor successfully created.";
    String DELIVERY_CHARGES_SUCCESS = "Delivery Charges successfully added.";

    String REPORT_COMPLAINT_SUCCESSFULLY = "Complaint registered successfully.";
    String BILL_PAID_SUCCESSFULLY = "Bill paid successfully.";

    String AMOUNT_RECEIVED = "Amount successfully received.";
    String AMOUNT_ALREADY_RECEIVED = "Amount already received. Please try again.";
    String AMOUNT_DIPENSED = "Amount successfully dispensed.";

    String SAROKH_TASK_SUBMITTED = "Sarokh task successfully submitted.";

    String DEALER_SUCCESSFULLY_UPDATED = "Dealer info successfully updated.";
    String DRIVER_SUCCESSFULLY_UPDATED = "Driver info successfully updated.";
    String VEHICLE_SUCCESSFULLY_UPDATED = "Vehicle info successfully updated.";
    String VENDOR_SUCCESSFULLY_UPDATED = "Vendor successfully updated.";

    String CODE_AMOUNT_RECEIVED = "CODE amount successfully received.";

    String CARD_SUCCESSFULLY_ASSIGNED = "Shipment successfully received at warhouse.";
    String DRIVER_SUCCESSFULLY_ASSIGNED = "Driver successfully received shipment.";

    String UNABLE_TO_DELETE = "Your requested item cannot be deleted. Please try again.";
    String UNABLE_TO_UPLOAD_FILE = "Your file cannot be uploaded. Please try again.";

    String UNABLE_TO_DELIVER = "This shipment is cannot be delivered. please try again.";
    String DELIVERED_ORDER = "This shipment is already delivered. please try again.";
    String STOLEN_OR_MISSING = "This shipment is stolen/missing. please try again.";
    String NOT_USER_DEFINED = "shipment location delivery location is not user defined. please try again.";

    String INVALID_INFO = "Invalid information provided. please try again.";
    String INVALID_ORDER_INFO = "Invalid shipment information provided. please try again.";
    String INVALID_ORDER_NUMBER = "Invalid shipment number provided. please try again.";
    String INVALID_TRANSACTION_NUMBER = "Invalid payment transaction info. please try again.";
    String INVALID_TRACKING_NUMBER = "Invalid tracking number provided. please try again.";
    String INVALID_OTP_NUMBER = "Invalid shipper OTP provided. please try again.";
    String INVALID_USER_DETAILS = "Invalid user details provided. please try again.";
    String INVALID_CURRENT_PASSWORD = "Invalid current password provided. please try again.";
    String INVALID_USERNAME_PASSWORD = "Invalid Username or Password. Please try again.";
    String INVALID_FILE_UPLOAD = "Please select a valid image to upload.";

    String INVALID_TRIP_DETAIL_INFO = "Invalid trip information provided. please try again.";
    String INVALID_ORDER_OR_CARD_NUMBER = "Invalid shipment or card number  provided. please try again.";
    String INVALID_ORDER_OR_DRIVER_INFO = "Invalid shipment number or driver info  provided. please try again.";

    String DELIVERY_CHARGES_EXISTS = "Delivery Charges already exists for this shipper.";
    String ALREADY_ACTIVE_DETAIL_TRIPS = "Cannot schedule trip. Driver has already active trips.";

}
