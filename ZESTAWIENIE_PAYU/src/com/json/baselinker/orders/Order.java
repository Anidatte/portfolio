
package com.json.baselinker.orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order_id",
    "external_order_id",
    "order_source",
    "order_source_id",
    "order_source_info",
    "order_status_id",
    "confirmed",
    "date_add",
    "date_confirmed",
    "date_in_status",
    "user_login",
    "phone",
    "email",
    "user_comments",
    "admin_comments",
    "currency",
    "payment_method",
    "payment_method_cod",
    "payment_done",
    "payment_date",
    "delivery_method",
    "delivery_price",
    "delivery_package_module",
    "delivery_package_nr",
    "delivery_fullname",
    "delivery_company",
    "delivery_address",
    "delivery_city",
    "delivery_postcode",
    "delivery_country",
    "delivery_point_name",
    "delivery_point_address",
    "delivery_point_postcode",
    "delivery_point_city",
    "invoice_fullname",
    "invoice_company",
    "invoice_nip",
    "invoice_address",
    "invoice_city",
    "invoice_postcode",
    "invoice_country",
    "want_invoice",
    "extra_field_1",
    "extra_field_2",
    "order_page",
    "products"
})
public class Order {

    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("external_order_id")
    private String externalOrderId;
    @JsonProperty("order_source")
    private String orderSource;
    @JsonProperty("order_source_id")
    private String orderSourceId;
    @JsonProperty("order_source_info")
    private String orderSourceInfo;
    @JsonProperty("order_status_id")
    private String orderStatusId;
    @JsonProperty("confirmed")
    private boolean orderConfirmed;
    @JsonProperty("date_add")
    private String dateAdd;
    @JsonProperty("date_confirmed")
    private String dateConfirmed;
    @JsonProperty("date_in_status")
    private String dateInStatus;
    @JsonProperty("user_login")
    private String userLogin;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("user_comments")
    private String userComments;
    @JsonProperty("admin_comments")
    private String adminComments;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("payment_method_cod")
    private String paymentMethodCod;
    @JsonProperty("payment_done")
    private Double paymentDone;
    @JsonProperty("payment_date")
    private String paymentDate;
    @JsonProperty("delivery_method")
    private String deliveryMethod;
    @JsonProperty("delivery_price")
    private Double deliveryPrice;
    @JsonProperty("delivery_package_module")
    private String deliveryPackageModule;
    @JsonProperty("delivery_package_nr")
    private String deliveryPackageNr;
    @JsonProperty("delivery_fullname")
    private String deliveryFullname;
    @JsonProperty("delivery_company")
    private String deliveryCompany;
    @JsonProperty("delivery_address")
    private String deliveryAddress;
    @JsonProperty("delivery_city")
    private String deliveryCity;
    @JsonProperty("delivery_postcode")
    private String deliveryPostcode;
    @JsonProperty("delivery_country")
    private String deliveryCountry;
    @JsonProperty("delivery_point_name")
    private String deliveryPointName;
    @JsonProperty("delivery_point_address")
    private String deliveryPointAddress;
    @JsonProperty("delivery_point_postcode")
    private String deliveryPointPostcode;
    @JsonProperty("delivery_point_city")
    private String deliveryPointCity;
    @JsonProperty("invoice_fullname")
    private String invoiceFullname;
    @JsonProperty("invoice_company")
    private String invoiceCompany;
    @JsonProperty("invoice_nip")
    private String invoiceNip;
    @JsonProperty("invoice_address")
    private String invoiceAddress;
    @JsonProperty("invoice_city")
    private String invoiceCity;
    @JsonProperty("invoice_postcode")
    private String invoicePostcode;
    @JsonProperty("invoice_country")
    private String invoiceCountry;
    @JsonProperty("want_invoice")
    private String wantInvoice;
    @JsonProperty("extra_field_1")
    private String extraField1;
    @JsonProperty("extra_field_2")
    private String extraField2;
    @JsonProperty("order_page")
    private String orderPage;
    @JsonProperty("products")
    private List<Product> products = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("external_order_id")
    public String getExternalOrderId() {
        return externalOrderId;
    }

    @JsonProperty("external_order_id")
    public void setExternalOrderId(String externalOrderId) {
        this.externalOrderId = externalOrderId;
    }

    @JsonProperty("order_source")
    public String getOrderSource() {
        return orderSource;
    }

    @JsonProperty("order_source")
    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    @JsonProperty("order_source_id")
    public String getOrderSourceId() {
        return orderSourceId;
    }

    @JsonProperty("order_source_id")
    public void setOrderSourceId(String orderSourceId) {
        this.orderSourceId = orderSourceId;
    }

    @JsonProperty("order_source_info")
    public String getOrderSourceInfo() {
        return orderSourceInfo;
    }

    @JsonProperty("order_source_info")
    public void setOrderSourceInfo(String orderSourceInfo) {
        this.orderSourceInfo = orderSourceInfo;
    }

    @JsonProperty("order_status_id")
    public String getOrderStatusId() {
        return orderStatusId;
    }

    @JsonProperty("order_status_id")
    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
    
    @JsonProperty("confirmed")
    public boolean getOrderConfirmed() {
        return orderConfirmed;
    }
    
    @JsonProperty("confirmed")
    public void setOrderConfirmed(boolean orderConfirmed) {
        this.orderConfirmed = orderConfirmed;
    }

    @JsonProperty("date_add")
    public String getDateAdd() {
        return dateAdd;
    }

    @JsonProperty("date_add")
    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    @JsonProperty("date_confirmed")
    public String getDateConfirmed() {
        return dateConfirmed;
    }

    @JsonProperty("date_confirmed")
    public void setDateConfirmed(String dateConfirmed) {
        this.dateConfirmed = dateConfirmed;
    }

    @JsonProperty("date_in_status")
    public String getDateInStatus() {
        return dateInStatus;
    }

    @JsonProperty("date_in_status")
    public void setDateInStatus(String dateInStatus) {
        this.dateInStatus = dateInStatus;
    }

    @JsonProperty("user_login")
    public String getUserLogin() {
        return userLogin;
    }

    @JsonProperty("user_login")
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("user_comments")
    public String getUserComments() {
        return userComments;
    }

    @JsonProperty("user_comments")
    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    @JsonProperty("admin_comments")
    public String getAdminComments() {
        return adminComments;
    }

    @JsonProperty("admin_comments")
    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("payment_method")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("payment_method")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("payment_method_cod")
    public String getPaymentMethodCod() {
        return paymentMethodCod;
    }

    @JsonProperty("payment_method_cod")
    public void setPaymentMethodCod(String paymentMethodCod) {
        this.paymentMethodCod = paymentMethodCod;
    }

    @JsonProperty("payment_done")
    public Double getPaymentDone() {
        return paymentDone;
    }

    @JsonProperty("payment_done")
    public void setPaymentDone(Double paymentDone) {
        this.paymentDone = paymentDone;
    }

    @JsonProperty("payment_date")
    public String getPaymentDate() {
        return paymentDate;
    }

    @JsonProperty("payment_date")
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    @JsonProperty("delivery_method")
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    @JsonProperty("delivery_method")
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @JsonProperty("delivery_price")
    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    @JsonProperty("delivery_price")
    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @JsonProperty("delivery_package_module")
    public String getDeliveryPackageModule() {
        return deliveryPackageModule;
    }

    @JsonProperty("delivery_package_module")
    public void setDeliveryPackageModule(String deliveryPackageModule) {
        this.deliveryPackageModule = deliveryPackageModule;
    }

    @JsonProperty("delivery_package_nr")
    public String getDeliveryPackageNr() {
        return deliveryPackageNr;
    }

    @JsonProperty("delivery_package_nr")
    public void setDeliveryPackageNr(String deliveryPackageNr) {
        this.deliveryPackageNr = deliveryPackageNr;
    }

    @JsonProperty("delivery_fullname")
    public String getDeliveryFullname() {
        return deliveryFullname;
    }

    @JsonProperty("delivery_fullname")
    public void setDeliveryFullname(String deliveryFullname) {
        this.deliveryFullname = deliveryFullname;
    }

    @JsonProperty("delivery_company")
    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    @JsonProperty("delivery_company")
    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    @JsonProperty("delivery_address")
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    @JsonProperty("delivery_address")
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @JsonProperty("delivery_city")
    public String getDeliveryCity() {
        return deliveryCity;
    }

    @JsonProperty("delivery_city")
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    @JsonProperty("delivery_postcode")
    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    @JsonProperty("delivery_postcode")
    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }

    @JsonProperty("delivery_country")
    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    @JsonProperty("delivery_country")
    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    @JsonProperty("delivery_point_name")
    public String getDeliveryPointName() {
        return deliveryPointName;
    }

    @JsonProperty("delivery_point_name")
    public void setDeliveryPointName(String deliveryPointName) {
        this.deliveryPointName = deliveryPointName;
    }

    @JsonProperty("delivery_point_address")
    public String getDeliveryPointAddress() {
        return deliveryPointAddress;
    }

    @JsonProperty("delivery_point_address")
    public void setDeliveryPointAddress(String deliveryPointAddress) {
        this.deliveryPointAddress = deliveryPointAddress;
    }

    @JsonProperty("delivery_point_postcode")
    public String getDeliveryPointPostcode() {
        return deliveryPointPostcode;
    }

    @JsonProperty("delivery_point_postcode")
    public void setDeliveryPointPostcode(String deliveryPointPostcode) {
        this.deliveryPointPostcode = deliveryPointPostcode;
    }

    @JsonProperty("delivery_point_city")
    public String getDeliveryPointCity() {
        return deliveryPointCity;
    }

    @JsonProperty("delivery_point_city")
    public void setDeliveryPointCity(String deliveryPointCity) {
        this.deliveryPointCity = deliveryPointCity;
    }

    @JsonProperty("invoice_fullname")
    public String getInvoiceFullname() {
        return invoiceFullname;
    }

    @JsonProperty("invoice_fullname")
    public void setInvoiceFullname(String invoiceFullname) {
        this.invoiceFullname = invoiceFullname;
    }

    @JsonProperty("invoice_company")
    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    @JsonProperty("invoice_company")
    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    @JsonProperty("invoice_nip")
    public String getInvoiceNip() {
        return invoiceNip;
    }

    @JsonProperty("invoice_nip")
    public void setInvoiceNip(String invoiceNip) {
        this.invoiceNip = invoiceNip;
    }

    @JsonProperty("invoice_address")
    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    @JsonProperty("invoice_address")
    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    @JsonProperty("invoice_city")
    public String getInvoiceCity() {
        return invoiceCity;
    }

    @JsonProperty("invoice_city")
    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    @JsonProperty("invoice_postcode")
    public String getInvoicePostcode() {
        return invoicePostcode;
    }

    @JsonProperty("invoice_postcode")
    public void setInvoicePostcode(String invoicePostcode) {
        this.invoicePostcode = invoicePostcode;
    }

    @JsonProperty("invoice_country")
    public String getInvoiceCountry() {
        return invoiceCountry;
    }

    @JsonProperty("invoice_country")
    public void setInvoiceCountry(String invoiceCountry) {
        this.invoiceCountry = invoiceCountry;
    }

    @JsonProperty("want_invoice")
    public String getWantInvoice() {
        return wantInvoice;
    }

    @JsonProperty("want_invoice")
    public void setWantInvoice(String wantInvoice) {
        this.wantInvoice = wantInvoice;
    }

    @JsonProperty("extra_field_1")
    public String getExtraField1() {
        return extraField1;
    }

    @JsonProperty("extra_field_1")
    public void setExtraField1(String extraField1) {
        this.extraField1 = extraField1;
    }

    @JsonProperty("extra_field_2")
    public String getExtraField2() {
        return extraField2;
    }

    @JsonProperty("extra_field_2")
    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }

    @JsonProperty("order_page")
    public String getOrderPage() {
        return orderPage;
    }

    @JsonProperty("order_page")
    public void setOrderPage(String orderPage) {
        this.orderPage = orderPage;
    }

    @JsonProperty("products")
    public List<Product> getProducts() {
        return products;
    }

    @JsonProperty("products")
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
