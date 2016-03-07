package com.makingdevs

import org.apache.poi.ss.usermodel.*
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.xssf.usermodel.*
import org.apache.poi.ss.util.*
import org.apache.poi.ss.usermodel.*

class CreateWorkbook{
  static void main(def args){
    def invoice=new CreateWorkbook()
    invoice.getAllInvoice("/Users/makingdevs/workspace/facturas/12_Diciembre")
    invoice.getAddendaInvoice("/Users/makingdevs/workspace/facturas/11-12-2015_4931730006062697.xml")
    invoice.detailInvoice("/Users/makingdevs/workspace/facturas/AOM920820BEA_FAC_FA47b04949-f4a0-4d84-8464-4ded7e4b282e_20151231.xml")
  }
  def detailInvoice(String path){
    XSSFWorkbook workbook = new XSSFWorkbook()
    XSSFSheet sheet=workbook.createSheet("Pagina_1")
    CreationHelper createHelper = workbook.getCreationHelper()
    XSSFCellStyle dateStyle = workbook.createCellStyle()
    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-YYYY HH:mm"))
    XSSFCellStyle headStyle = workbook.createCellStyle()
    headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex())
    headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND)
    List<Comprobante> comprobantes=[]
    def parseXML=new ParseXML()
    def comprobante=new Comprobante()
    comprobantes.add(parseXML.readFile(path))
    
    int row=0,cellnum=0

    def titles = ["Fecha","Subtotal","Descuento","Impuesto",
    "Total","Emisor","Receptor","NoCertificado","Sello","Folio",
    "FormaDePago","Addenda","LugarExpedicion","TimbreFiscalDigital",
    "TipoDeComprobante","TipoDeCambio","Serie","Moneda","NumCtaPago",
    "Conceptos","Certificado","MetodoDePago"]


    comprobantes.each{ factura->
      Row r = sheet.createRow(row++)
      if(row==1){
        Cell c = r.createCell(cellnum++)
        titles.eachWithIndex { t, i ->
          c.setCellStyle(headStyle)
          c.setCellValue(t)
          c = r.createCell(cellnum + i)
        }
        
        cellnum=0
      }
      row=1
    }

    comprobantes.each{factura->
      Row r = sheet.createRow(row++)
      Cell c = r.createCell(cellnum++)
      c.setCellStyle(dateStyle)
      c.setCellValue(factura.fecha)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.subTotal)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.descuento)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.impuesto.totalImpuestosTrasladado)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.total)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.nombre)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.nombre)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.noCertificado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.sello)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.folio)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.formaDePago)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.addenda.toString())
      c = r.createCell(cellnum++)
      c.setCellValue(factura.lugarExpedicion)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.uuid)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.tipoDeComprobante)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.tipoCambio)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.serie)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.moneda)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.numCtaPago)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.conceptos.toString())
      c = r.createCell(cellnum++)
      c.setCellValue(factura.certificado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.metodoDePago)
      
      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Emisor RFC")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Nombre")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Pais")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Municipio")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Estado")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Codigo Postal")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Calle")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Regimen")

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.rfc)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.nombre)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.domicilioFiscal.pais)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.domicilioFiscal.municipio)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.domicilioFiscal.estado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.domicilioFiscal.codigoPostal)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.domicilioFiscal.calle)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.regimen.regimen)

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Receptor RFC")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Nombre")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Pais")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Municipio")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Estado")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Codigo Postal")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Calle")

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.rfc)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.nombre)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.direccionReceptor.pais)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.direccionReceptor.municipio)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.direccionReceptor.estado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.direccionReceptor.codigoPostal)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.direccionReceptor.calle)
      
      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Cantidad")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Unidad")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("NoIdentificacion")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Descripcion")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("ValorUnitario")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Importe")

      factura.conceptos.each{detalle->
        cellnum=0
        r = sheet.createRow(row++)
        c = r.createCell(cellnum++)

        c.setCellValue(detalle.cantidad)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.unidad)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.noIdentificacion)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.descripcion)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.valorUnitario)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.importe)
      }

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Total Impuestos Trasladados")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Traslado importe")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Traslado Tasa")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Traslado Impuesto")

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.impuesto.totalImpuestosTrasladado)
      factura.impuesto.traslado.each{detalle->
        cellnum=1
        r = sheet.createRow(row++)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.importe)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.tasa)
        c = r.createCell(cellnum++)
        c.setCellValue(detalle.impuesto)
      }

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Fecha timbrado")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("UUID")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("No Certificado SAT")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Sello CFD")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Sello SAT")
      c = r.createCell(cellnum++)
      c.setCellStyle(headStyle)
      c.setCellValue("Version")

      cellnum=0
      r = sheet.createRow(row++)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.fechaTimbrado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.uuid)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.noCertificadoSAT)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.selloCFD)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.selloSAT)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.version)

    }

    FileOutputStream out = new FileOutputStream(new File("Libro_Factura_Detalle.xlsx"))
    workbook.write(out)
    out.close()

  }

  def getAddendaInvoice(String path){
    XSSFWorkbook workbook = new XSSFWorkbook()
    XSSFSheet sheet=workbook.createSheet("Pagina_1")
    CreationHelper createHelper = workbook.getCreationHelper()
    XSSFCellStyle dateStyle = workbook.createCellStyle()
    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-YYYY HH:mm"))
    XSSFCellStyle headStyle = workbook.createCellStyle()
    headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex())
    headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND)
    List<Comprobante> comprobantes=[]
    def parseXML=new ParseXML()
    def comprobante=new Comprobante()
    comprobantes.add(parseXML.readFile(path))
    
    int row=0,cellnum=0

    comprobantes.each{factura->
      Row r = sheet.createRow(row++)
      if(row==1){
        Cell c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Serie")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Fecha")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Subtotal")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Descuento")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Impuesto")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Total")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Addenda periodo")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Sucursal")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("NumeroCuenta")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("NombreCliente")
        c.setCellStyle(headStyle)
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Version")
        cellnum=0
      }
      row=1
    }

    comprobantes.each{factura->

      Row r = sheet.createRow(row++)
      Cell c = r.createCell(cellnum++)
        c.setCellValue(factura.serie)
        c = r.createCell(cellnum++)
        
        c.setCellValue(factura.fecha)
        c.setCellStyle(dateStyle)
        
        c = r.createCell(cellnum++)
        c.setCellValue(factura.subTotal)
        c = r.createCell(cellnum++)
        c.setCellValue(factura.descuento)
        c = r.createCell(cellnum++)
        c.setCellValue(factura.impuesto.totalImpuestosTrasladado)
        c = r.createCell(cellnum++)
        c.setCellValue(factura.total)
        
        factura.addenda.estadoDeCuentaBancario.getProperties().each{detalle->
          if(!detalle.getKey().equalsIgnoreCase("class")){
            if(detalle.getKey().equalsIgnoreCase("movimientoECB")){
              factura.addenda.estadoDeCuentaBancario.movimientoECB.each{movimientosECB->
                cellnum=0
                r = sheet.createRow(row++)
                c = r.createCell(cellnum)
                movimientosECB.getProperties().each{movimiento->
                  if(row==3){
                    movimiento.each{descripcion->
                      if(!descripcion.getKey().equalsIgnoreCase("class")){
                        c = r.createCell(cellnum++)
                        c.setCellStyle(headStyle)
                        c.setCellValue(descripcion.getKey().capitalize())
                      }
                    }
                  }
                }
              }
              row=3
              cellnum=0
              factura.addenda.estadoDeCuentaBancario.movimientoECB.each{movimientosECB->
                r = sheet.createRow(row++)
                c = r.createCell(cellnum)
                movimientosECB.getProperties().each{movimiento->
                  
                    movimiento.each{descripcion->
                      if(!descripcion.getKey().equalsIgnoreCase("class")){
                        c = r.createCell(cellnum++)
                        if(descripcion.getKey().equalsIgnoreCase("importe") || 
                          descripcion.getKey().equalsIgnoreCase("saldoInicial") ||
                          descripcion.getKey().equalsIgnoreCase("saldoAlCorte")){
                          c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
                          c.setCellValue(descripcion.getValue())
                        }
                        else if(descripcion.getKey().equalsIgnoreCase("fecha")){
                          c.setCellValue(descripcion.getValue())
                          c.setCellStyle(dateStyle)
                          
                        }
                        else{
                          c.setCellValue(descripcion.getValue().toString())  
                        }
                        
                      }
                    }
                }
                cellnum=0
              }
            }
            else{
              c = r.createCell(cellnum++)
              c.setCellValue(detalle.getValue().toString())
            }
          }
        }
    }
    
    FileOutputStream out = new FileOutputStream(new File("Libro_Factura_Movimientos.xlsx"))
    workbook.write(out)
    out.close()
  }

  def getAllInvoice(String path){
    XSSFWorkbook workbook = new XSSFWorkbook()
    XSSFSheet sheet=workbook.createSheet("Pagina_1")
    CreationHelper createHelper = workbook.getCreationHelper()
    XSSFCellStyle dateStyle = workbook.createCellStyle()
    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-YYYY HH:mm"))
    XSSFCellStyle headStyle = workbook.createCellStyle()
    headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex())
    headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND)

    def parseXML=new ParseXML()
    def comprobante=new Comprobante()

    List<String> filesXML=parseXML.getFilesXML(path)
    List<Comprobante> comprobantes=[]
    filesXML.each{f->
      comprobantes.add(parseXML.readFile(f))
    }
    
    int row=0,cellnum=0

    comprobantes.each{factura->
      Row r = sheet.createRow(row++)
      if(row==1){
        Cell c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Fecha")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Subtotal")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Descuento")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Impuesto")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Total")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Emisor")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Receptor")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("NoCertificado")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Sello")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Folio")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("FormaDePago")
        c.setCellStyle(headStyle)
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Addenda")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("LugarExpedicion")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("TimbreFiscalDigital")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("TipoDeComprobante")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("TipoDeCambio")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Serie")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Moneda")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("NumCtaPago")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Conceptos")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("Certificado")
        c = r.createCell(cellnum++)
        c.setCellStyle(headStyle)
        c.setCellValue("MetodoDePago")
        cellnum=0
      }
      row=1
    }

    comprobantes.each{factura->
      Row r = sheet.createRow(row++)
      Cell c = r.createCell(cellnum++)
      c.setCellStyle(dateStyle)
      c.setCellValue(factura.fecha)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.subTotal)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.descuento)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.impuesto.totalImpuestosTrasladado)
      c = r.createCell(cellnum++)
      c.setCellType(XSSFCell.CELL_TYPE_NUMERIC)
      c.setCellValue(factura.total)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.emisor.nombre)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.receptor.nombre)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.noCertificado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.sello)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.folio)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.formaDePago)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.addenda)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.lugarExpedicion)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.timbreFiscalDigital.uuid)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.tipoDeComprobante)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.tipoCambio)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.serie)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.moneda)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.numCtaPago)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.conceptos.toString())
      c = r.createCell(cellnum++)
      c.setCellValue(factura.certificado)
      c = r.createCell(cellnum++)
      c.setCellValue(factura.metodoDePago)
      cellnum=0
    }
    FileOutputStream out = new FileOutputStream(new File("Libro_Facturas.xlsx"))
    workbook.write(out)
    out.close()
  }
  
}