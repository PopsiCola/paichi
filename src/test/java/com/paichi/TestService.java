package com.paichi;

import com.paichi.modules.craft.entity.Craft;
import com.paichi.modules.craft.service.ICraftService;
import com.paichi.modules.effect.entity.Effect;
import com.paichi.modules.effect.service.IEffectService;
import com.paichi.modules.materials.entity.Materials;
import com.paichi.modules.materials.service.IMaterialsService;
import com.paichi.modules.recipe.entity.Taste;
import com.paichi.modules.recipe.service.ITasteService;
import com.paichi.modules.record.service.IFileRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author liulebin
 * @Date 2020/9/22 17:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestService {

    @Autowired
    private IFileRecordService fileRecordService;
    @Autowired
    private ICraftService craftService;

    @Autowired
    private IEffectService effectService;

    @Autowired
    private IMaterialsService materialsService;

    @Autowired
    private ITasteService tasteService;


    @Test
    public void testSave() {
        Craft craft = new Craft();
        craft.setCraftName("gg");

        Integer integer = craftService.addCraft(craft);
        System.out.println(integer);
    }
    @Test
    public void testSave2() {
        Effect effect = new Effect();
        effect.setEffectName("afa");

        Integer integer = effectService.addEffect(effect);
        System.out.println(integer);
    }

    @Test
    public void testSave3() {
        //Materials materials = new Materials();
        Taste taste = new Taste();
        taste.setTasteName("AFASFS");

        Integer integer = tasteService.addTaste(taste);
        System.out.println(integer);
    }


}
