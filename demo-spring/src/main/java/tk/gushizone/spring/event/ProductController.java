package tk.gushizone.spring.event;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gushizone
 * @date 2022/10/17 15:04
 */
@RestController("/event/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/save")
    public String save() {

        productService.save(null);
        return "ok";
    }

}
