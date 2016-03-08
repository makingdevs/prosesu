package com.makingdevs.service.impl
import com.makingdevs.service.InvoiceService
import com.makingdevs.Comprobante
import com.makingdevs.Emisor
import com.makingdevs.Receptor
import com.makingdevs.Concepto
import com.makingdevs.Impuesto
import com.makingdevs.TimbreFiscalDigital
import com.makingdevs.Addenda
import com.makingdevs.Direccion
import com.makingdevs.RegimenFiscal

class InvoiceServiceImpl implements InvoiceService{
  Comprobante obtainVoucherFromInvoice(File invoice){
    def voucher= new Comprobante()
    def xml = new XmlSlurper().parseText(invoice.getText()).declareNamespace(
      cfdi:"http://www.sat.gob.mx/cfd/3",
      xsi:"http://www.w3.org/2001/XMLSchema-instance")
    xml.attributes().each{attributeXML->
      voucher.getProperties().each{attributeVoucher->
        if(attributeXML.key.equalsIgnoreCase(attributeVoucher.key)){
          if(attributeVoucher.key.equalsIgnoreCase("fecha")){
            voucher[attributeVoucher.key]=Date.parse("yyyy-MM-dd'T'HH:mm:ss", attributeXML.value)
          }
          else if(["total","subTotal","descuento","tipoCambio"]*.toLowerCase().contains(attributeVoucher.key.toLowerCase())){ 
            voucher[attributeVoucher.key]=new BigDecimal(attributeXML.value)
          }
          else{
            voucher[attributeVoucher.key]=attributeXML.value
          }
        }
      }
    }
    voucher
  }

  Emisor obtainTransmitterFromInvoice(File invoice){
    def voucher= new Comprobante()
    def emisor=new Emisor()
    def domicilioFiscal=new Direccion()
    def lugarExpedicion=new Direccion()
    def regimenFiscal=new RegimenFiscal()
    
    def xml = new XmlSlurper().parseText(invoice.getText()).declareNamespace(
      cfdi:"http://www.sat.gob.mx/cfd/3",
      xsi:"http://www.w3.org/2001/XMLSchema-instance")
    xml.Emisor.each{atributo->
      emisor.rfc=atributo.@rfc
      emisor.nombre=atributo.@nombre
      regimenFiscal.regimen=atributo.RegimenFiscal.@Regimen
      emisor.regimen=regimenFiscal
      voucher.emisor=emisor
    }
    xml.ExpedidoEn.each{atributo->
      lugarExpedicion.calle=atributo.@calle
      lugarExpedicion.noExterior=atributo.@noExterior
      lugarExpedicion.colonia=atributo.@colonia
      lugarExpedicion.municipio=atributo.@municipio
      lugarExpedicion.estado=atributo.@estado
      lugarExpedicion.pais=atributo.@pais
      lugarExpedicion.codigoPostal=atributo.@codigoPostal
      emisor.lugarExpedicion=lugarExpedicion
      voucher.emisor=emisor
    }
    xml.Emisor.DomicilioFiscal.each{atributo->
      domicilioFiscal.calle=atributo.@calle
      domicilioFiscal.noExterior=atributo.@noExterior
      domicilioFiscal.noInterior=atributo.@noInterior
      domicilioFiscal.colonia=atributo.@colonia
      domicilioFiscal.municipio=atributo.@municipio
      domicilioFiscal.estado=atributo.@estado
      domicilioFiscal.pais=atributo.@pais
      domicilioFiscal.codigoPostal=atributo.@codigoPostal
      emisor.domicilioFiscal=domicilioFiscal
      voucher.emisor=emisor
    }
    emisor
  }

  Receptor obtainReceiverFromInvoice(File invoice){

  }

  List<Concepto> obtainConceptsFromInvoice(File invoice){

  }

  Impuesto obtainTaxesFromInvoice(File invoice){

  }

  TimbreFiscalDigital obtainDigitalTaxStampFromInvoice(File invoice){

  }

  Addenda obtainAddendaFromInvoice(File invoice){

  }
  
}
