package com.wasu.es;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MASTER_L on 2018/1/12.
 */
public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            System.out.println(sdf.parse("2017.11.15").getTime());
            System.out.println(sdf.parse("2017.11.22").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
