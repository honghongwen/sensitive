package cn.tanzhou.sensitive.example;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author fengwen
 * @date 2020-12-29
 * 测试拦截器
 */
@RequestMapping
@RestController
public class ExampleController {

    @PostMapping("/save")
    public String save(@Validated @RequestBody SaveParam param) {
        return param.getTitle();
    }

    @GetMapping("/str")
    public String str(@RequestParam String param) {
        return param;
    }

}
