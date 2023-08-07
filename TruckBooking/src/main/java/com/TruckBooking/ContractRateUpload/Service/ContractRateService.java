package com.TruckBooking.ContractRateUpload.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.TruckBooking.ContractRateUpload.Dao.ContractRateRepo;
import com.TruckBooking.ContractRateUpload.Dao.IndentDao;
import com.TruckBooking.ContractRateUpload.Entity.Rates;
import com.TruckBooking.TruckBooking.Dao.LoadDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContractRateService {

	@Autowired
	LoadDao loadDao;

	@Autowired
    ContractRateRepo contractpricerepo;

	@Autowired
    IndentDao indentdao;

    // check that file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }

    }

    // convert excel to list of products

    public static List<Rates> convertExcelToListOfProduct(InputStream is) {
        List<Rates> list = new ArrayList<>();

        try {

            try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
                XSSFSheet sheet = workbook.getSheet("Sheet1");

                int rowNumber = 0;
                Iterator<Row> iterator = sheet.iterator();

                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    int flag = 0;
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cells = row.iterator();

                    int cid = 0;

                    Rates p = new Rates();

                    while (cells.hasNext()) {
                        Cell cell = cells.next();

                        switch (cid) {
                            case 0:
                                try {
                                    p.setUnLoadingPoint(cell.getStringCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            case 1:
                                try {
                                    p.setWeight(cell.getStringCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            case 2:
                                try {
                                    p.setRate((int) cell.getNumericCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            case 3:
                                try {
                                    p.setTransporterId((int) cell.getNumericCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            case 4:
                                try {
                                    p.setTransporterEmail(cell.getStringCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            case 5:
                                try {
                                    p.setTransporterName(cell.getStringCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            case 6:
                                try {
                                    p.setLoadingPoint(cell.getStringCellValue());
                                } catch (Exception e) {
                                    flag = 1;
                                }
                                break;
                            default:
                                 break;
                        }
                        cid++;
                    }

                    if (flag == 1) {
                        continue;
                    } else {                        
                        list.add(p);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.print(list.size());
        return list;
    }
    
	// this function helps us to save excel file.
    public void save(MultipartFile file) {

        try {
            List<Rates> products = ContractRateService.convertExcelToListOfProduct(file.getInputStream());            
            this.contractpricerepo.saveAll(products);
            System.out.println("Saved");
        } catch (IOException e) {
            System.out.println("Error is error");
            e.printStackTrace();
        }
    }
}