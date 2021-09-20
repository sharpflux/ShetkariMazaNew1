package com.sharpflux.shetkarimaza.volley;

public class URLs {

    public static  final  String Main_URL="http://kisanmaza.sharpflux.com/";

    public static final String URL_REGISTER = Main_URL+"api/Registration/User";
    public static final String URL_LOGIN = Main_URL+"api/Users/Login";
    public static final String URL_RECYCLER=Main_URL+"api/Categories/GetCategories?Language=";
    public static final String URL_RType=Main_URL+"api/Categories/GetRegType?StartIndex=";
    public static final String URL_RCategary=Main_URL+"api/Categories/RegCat";
    public static final String URL_QUALITY=Main_URL+"api/Quality/GetQuality";
    public static final String URL_BANK=Main_URL+"api/Bank/GetBank";
    public static final String URL_UNIT=Main_URL+"api/Measurement/GetMeasurementAPI";
    public static final String URL_NAME=Main_URL+"api/ItemMasterApi/Get_ItemMaster?StartIndex=";
    public static final String URL_VARIATY=Main_URL+"api/VarietyMaster/Get_VarietyMaster?ItemTypeId=";
    public static final String URL_SAVEPRODUCTDETAILS = Main_URL+"api/RequestAPI/request";
    public static  final String URL_REQESTS=Main_URL+"api/request/RequestsGET";
    public static final String URL_STATE=Main_URL+"api/States/GETSTATE";
    public static final String URL_GETBANKS=Main_URL+"api/Registration/GetBanks";
    public static  final String URL_DISTRICT=Main_URL+"Api/DistrictMultiple/Get_DistrictMultiple?StatesID=";
    public static final String URL_OTP=Main_URL+"User_Authentication/OTPGenerate";
    public static final String URL_TALUKA=Main_URL+"Api/GetMultipleTalukasApi/GetMultipleTalukas?DistrictsID=";
    public static final String URL_RESETPASS=Main_URL+"api/UserUdatePasswordAPI/UpdatePasswordApi";
    public static final String URL_ORDERDETAILS=Main_URL+"api/RequestAPI/GETRequestsApi?StartIndex=1&PageSize=500&UserId=";
    public static final String URL_RATE=Main_URL+"api/RateMasterAPI/GETRateMasterApi?";
    public static String URL_OTP2=Main_URL+"User_Authentication/GetOTP?MobileNo=";
    public static String URL_CONTACTDET=Main_URL+"api/Registration/RegistrationFilterGET?";
    public static String URL_PROCESSOR=Main_URL+"api/ItemMasterApi/GetIsProcessAPI?Language=";
    public static String URL_VEHICLETYPE=Main_URL+"api/Vehical/Get_VehicalTypeAPI";
    public static String URL_ALL_TRANSPORTER=Main_URL+"api/TransporterAPI/Get_TransporterDetailsAPI?Search=s";
    public static String URL_TRANSPORTER_DETAILS=Main_URL+"api/TransporterAPI/TransporterVehicalDetails?UserId=1";
    public static String URL_SUBcATEGORY=Main_URL+"api/ItemMasterApi/Get_SubCatItems?ItemTypeId=";
    public static String URL_AGE=Main_URL+"api/AgeGroupAPI/GetAgeGroupAPI?";

    public static String URL_REGISTRATIONGETUSERDETAILS=Main_URL+"api/Registration/RegistrationGetUserDetails?";

    public static String URL_GETVEHICLE=Main_URL+"api/Registration/GetVehicle?";
    public static String URL_POSTVEHICLE=Main_URL+"api/TransporterAPI/TransportMasterInsertUpdateAndroid";
    public static String URL_GETTRANSPORTERS=Main_URL+"api/Registration/TransporterGet?";

    public static final String URL_GENERATEOTP=Main_URL+"api/Utilities/GetOtpAndAddUpdateUser";
    public static final String URL_VERIFYUSER=Main_URL+"api/Registration/RegistrationUpdateIsVerified" ;
    public static final String URL_GetAvailableMonthsDynamic=Main_URL+"api/RequestAPI/GetAvailableMonthsDynamic?StartIndex=0" ;
    public static final String URL_GovtDepartmentGet=Main_URL+"api/Govt/GovtDepartmentGet";


  /*  public static final String URL_LOGIN = "http://apimaza.supergo.in/api/Users/Login";
    public static final String URL_RECYCLER="http://apimaza.supergo.in/api/Categories/GetCategories?Language=";
    public static final String URL_RType="http://apimaza.supergo.in/api/Categories/GetRegType?StartIndex=";
    public static final String URL_RCategary="http://apimaza.supergo.in/api/Categories/RegCat";
    public static final String URL_QUALITY="http://apimaza.supergo.in/api/Quality/GetQuality";
    public static final String URL_BANK="http://apimaza.supergo.in/api/Bank/GetBank";
    public static final String URL_UNIT="http://apimaza.supergo.in/api/Measurement/GetMeasurementAPI";
    public static final String URL_NAME="http://apimaza.supergo.in/api/ItemMasterApi/Get_ItemMaster?StartIndex=";
    public static final String URL_VARIATY="http://apimaza.supergo.in/api/VarietyMaster/Get_VarietyMaster?ItemTypeId=";
    public static final String URL_SAVEPRODUCTDETAILS = "http://apimaza.supergo.in/api/RequestAPI/request";
    public static  final String URL_REQESTS="http://apimaza.supergo.in/api/request/RequestsGET";
    public static final String URL_STATE="http://apimaza.supergo.in/api/States/GETSTATE";
    public static  final String URL_DISTRICT="http://apimaza.supergo.in/Api/DistrictMultiple/Get_DistrictMultiple?StatesID=";
    public static final String URL_OTP="http://apimaza.supergo.in/User_Authentication/OTPGenerate";
    public static final String URL_TALUKA="http://apimaza.supergo.in/Api/GetMultipleTalukasApi/GetMultipleTalukas?DistrictsID=";
    public static final String URL_RESETPASS="http://apimaza.supergo.in/api/UserUdatePasswordAPI/UpdatePasswordApi";
    public static final String URL_ORDERDETAILS="http://apimaza.supergo.in/api/RequestAPI/GETRequestsApi?StartIndex=1&PageSize=500&UserId=";
    public static final String URL_RATE="http://apimaza.supergo.in/api/RateMasterAPI/GETRateMasterApi?";
    public static String URL_OTP2="http://apimaza.supergo.in/User_Authentication/GetOTP?MobileNo=";
    public static String URL_CONTACTDET="http://apimaza.supergo.in/api/Registration/RegistrationFilterGET?StartIndex=1&PageSize=500";
    public static String URL_PROCESSOR="http://apimaza.supergo.in/api/ItemMasterApi/GetIsProcessAPI?Language=";
    public static String URL_VEHICLETYPE="http://apimaza.supergo.in/api/Vehical/Get_VehicalTypeAPI";
    public static String URL_ALL_TRANSPORTER="http://apimaza.supergo.in/api/TransporterAPI/Get_TransporterDetailsAPI?Search=s";
    public static String URL_TRANSPORTER_DETAILS="http://apimaza.supergo.in/api/TransporterAPI/TransporterVehicalDetails?UserId=1";
    public static String URL_SUBcATEGORY="http://apimaza.supergo.in/api/ItemMasterApi/Get_SubCatItems?ItemTypeId=";
    public static String URL_AGE="http://apimaza.supergo.in/api/AgeGroupAPI/GetAgeGroupAPI?";*/


}
