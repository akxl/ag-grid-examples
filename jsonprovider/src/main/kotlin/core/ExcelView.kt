package core

import org.apache.poi.ss.usermodel.Workbook
import org.springframework.web.servlet.view.document.AbstractXlsxView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExcelView: AbstractXlsxView() {

    override fun buildExcelDocument(model: MutableMap<String, Any>?, workbook: Workbook?, request: HttpServletRequest?, response: HttpServletResponse?) {

        response?.setHeader("Content-Disposition", "attachement; filename=\"my-file.xlsx\"")

        val sheet = workbook?.createSheet()
        val header = sheet?.createRow(0)
        header?.createCell(0)?.setCellValue(1.0)


    }



}