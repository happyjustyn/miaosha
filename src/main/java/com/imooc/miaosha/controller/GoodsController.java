package com.imooc.miaosha.controller;

import cn.hutool.core.util.StrUtil;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.GoodsKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsDetailVo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/2 22:15
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    /**
     * 框架自动管理
     */
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * QPS:96
     * 5000*2
     * QPS:300
     */
    @GetMapping(value = "/to_list", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        model.addAttribute("user", user);

        // 取页面缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StrUtil.isBlank(html)) {
            return html;
        }

        // 手动渲染
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
//        return "goods_list";
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StrUtil.isBlank(html)) {
            // 写入缓存
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @GetMapping(value = "/to_detail2", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String toDetail2(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                            @RequestParam("goodsId") Long goodsId) {
        model.addAttribute("user", user);

        // 取页面缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);
        if (!StrUtil.isBlank(html)) {
            return html;
        }

        // 手动渲染
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);
        // 还多久秒杀开始
        int remainSeconds = 0;
        // 秒杀状态判断
        int miaoshaStatus = 0;
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if (now < startAt) {
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
//        return "goods_detail";
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StrUtil.isBlank(html)) {
            // 写入缓存
            redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        }
        return html;
    }

    @GetMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(MiaoshaUser user, @PathVariable("goodsId") Long goodsId) {
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        // 还多久秒杀开始
        int remainSeconds = 0;
        // 秒杀状态判断
        int miaoshaStatus = 0;
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if (now < startAt) {
            remainSeconds = (int) (startAt - now) / 1000;
        } else if (now > endAt) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
        }
        GoodsDetailVo detailVo = new GoodsDetailVo();
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setMiaoshaStatus(miaoshaStatus);
        detailVo.setGoods(goodsVo);
        detailVo.setUser(user);
        return Result.success(detailVo);
    }


}
