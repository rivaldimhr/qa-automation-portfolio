<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Ubah sebagian data booking. Butuh token (cookie) atau Basic auth.</description>
   <name>PATCH_PartialUpdateBooking</name>
   <tag></tag>
   <elementGuidId>b84c7d51-80af-554f-9faf-f8cd23cfd06e</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;${body}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>5bdfd87f-0c65-5e80-9beb-5f83b3175a80</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>74d33f66-efa1-5b0c-b2fb-d39fa164e72b</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Cookie</name>
      <type>Main</type>
      <value>token=${token}</value>
      <webElementGuid>1aef69ad-3a83-521b-aad0-39fea856d57d</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>${authorization}</value>
      <webElementGuid>63b3dbb6-f1ab-5aa7-ab5f-a0b98e16008f</webElementGuid>
   </httpHeaderProperties>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>PATCH</restRequestMethod>
   <restUrl>${baseUrl}/booking/${id}</restUrl>
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
      <id>1fc1fc59-7f7e-53f8-bee9-bc636785c6a5</id>
      <masked>false</masked>
      <name>baseUrl</name>
   </variables>
   <variables>
      <defaultValue>'1'</defaultValue>
      <description>bookingid</description>
      <id>7b6d9190-83d7-51d0-aa31-c73c0844938f</id>
      <masked>false</masked>
      <name>id</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description>Token dari POST /auth; kosong = tanpa cookie token</description>
      <id>e3936661-9ba8-53ac-92ac-bc5b0987d4aa</id>
      <masked>false</masked>
      <name>token</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description>Header Authorization, misalnya Basic YWRtaW46cGFzc3dvcmQxMjM=; kosong = tanpa Basic auth</description>
      <id>bae7c3f5-f665-5aad-8c5d-526d66213d43</id>
      <masked>false</masked>
      <name>authorization</name>
   </variables>
   <variables>
      <defaultValue>'{&quot;additionalneeds&quot;: &quot;Late checkout&quot;}'</defaultValue>
      <description>Kolom yang diubah (JSON)</description>
      <id>092a118e-f932-5b2e-8bbe-ff1ce79fea10</id>
      <masked>false</masked>
      <name>body</name>
   </variables>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
