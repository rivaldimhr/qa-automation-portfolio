<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Cari bookingid berdasarkan nama.</description>
   <name>GET_SearchBooking</name>
   <tag></tag>
   <elementGuidId>abae46e4-a210-5ba2-bc47-0df995968775</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>cbb69378-7f43-53ad-a7f0-77677f1b257e</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>${baseUrl}/booking?firstname=${firstname}&amp;lastname=${lastname}</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.RB_URL</defaultValue>
      <description>Alamat Restful-Booker (dari profil)</description>
      <id>bec3bc2b-d9cf-5810-a15d-bc58590bc10d</id>
      <masked>false</masked>
      <name>baseUrl</name>
   </variables>
   <variables>
      <defaultValue>'Rivaldi'</defaultValue>
      <description>Nama depan</description>
      <id>03337eed-28c2-5692-916e-6f939b471647</id>
      <masked>false</masked>
      <name>firstname</name>
   </variables>
   <variables>
      <defaultValue>'QA'</defaultValue>
      <description>Nama belakang</description>
      <id>da4fa282-ce9f-5fdf-b227-d962da240517</id>
      <masked>false</masked>
      <name>lastname</name>
   </variables>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
