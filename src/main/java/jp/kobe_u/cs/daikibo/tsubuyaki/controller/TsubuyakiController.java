package jp.kobe_u.cs.daikibo.tsubuyaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.tsubuyaki.entity.TsubuyakiForm;
import jp.kobe_u.cs.daikibo.tsubuyaki.entity.TsubuyakiSearchForm;
import jp.kobe_u.cs.daikibo.tsubuyaki.service.TsubuyakiService;

@Controller
public class TsubuyakiController {
    @Autowired
    TsubuyakiService ts;
    
    @GetMapping( "/" )
    String showIndex() {
        return "index";
    }

    @GetMapping( "/read" )
    String showTsubuyakiList( Model model ) {
        List<Tsubuyaki> list = ts.getAllTsubuyaki(); //全つぶやきを取得

        model.addAttribute( "tsubuyakiList", list ); //モデル属性にリストをセット
        model.addAttribute( "tsubuyakiForm", new TsubuyakiForm() ); //空フォームをセット
        model.addAttribute( "tsubuyakiSearchForm", new TsubuyakiSearchForm() );

        return "tsubuyaki_list";
    }

    @PostMapping( "/read" )
    String postTsubuyaki( @ModelAttribute( "tsubuyakiForm" ) TsubuyakiForm form, Model model ) {
        Tsubuyaki t = new Tsubuyaki();

        //フォームからエンティティに移し替え
        t.setName( form.getName() );
        t.setComment( form.getComment() );

        //サービスに投稿処理を依頼
        ts.postTsubuyaki( t );
        return "redirect:/read";
    }

    @PostMapping( "/search" )
    String searchTsubuyaki( @ModelAttribute( "tsubuyakiSearchForm" ) TsubuyakiSearchForm form, Model model ) {
        List<Tsubuyaki> list = ts.searchTsubuyaki( form.getSearchWord() );

        model.addAttribute( "searchResult", list );

        if ( list.size() > 0 ) model.addAttribute( "resultText", list.size() + "件のつぶやきが見つかりました" );
        else model.addAttribute( "resultText", "指定していただいたキーワードを含むつぶやきはありませんでした..." );

        return "search_result";
    }
}