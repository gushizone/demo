package tk.gushizone.excel.easyexcel.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.gushizone.excel.easyexcel.utils.EasyExcelUtils;
import tk.gushizone.excel.easyexcel.utils.write.model.ExcelModel;
import tk.gushizone.excel.easyexcel.entity.Item;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-11-08 21:26
 */
@Slf4j
@RestController
@RequestMapping("easyexcel")
public class EasyExcelController {


    @GetMapping("export")
    public void export(HttpServletResponse response) {

        List<Item> items = Lists.newArrayList(new Item(0, "abc测试", new Date()),
                new Item(1, "1测试", new Date()),
                new Item(2, "2测试", new Date()),
                new Item(3, "3测试", new Date()),
                new Item(4, "4测试", new Date())
        );

        ExcelModel excelModel = ExcelModel.builder()
                .fileName("abc测试.xlsx")
                .sheets(Lists.newArrayList(
                        ExcelModel.Sheet.<Item>builder()
                                .headClass(Item.class)
                                .data(items)
                                .build()
                ))
                .build();

        EasyExcelUtils.write(response, excelModel);

    }

    @PostMapping("import")
    public void export(MultipartFile file) {

        List<Item> results = EasyExcelUtils.read(file, Item.class);

        log.info(results.toString());
    }

}
