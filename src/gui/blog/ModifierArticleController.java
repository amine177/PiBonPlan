/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.blog;

import entites.Article;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import security.Authenticator;
import services.ArticleService;

/**
 * FXML Controller class
 *
 * @author aminos
 */
public class ModifierArticleController implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private HTMLEditor editor;
    private Article article;
    BlogContainerController blogController;
    @FXML
    private AnchorPane blogWidget;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifierArticle(ActionEvent event) throws IOException {
         ArticleService aS = new ArticleService();
      
        String texte = editor.getHtmlText();
        Document doc = Jsoup.parseBodyFragment(texte);
        
        String htmlText = doc.body().html();
        
        String titre = this.titre.getText();
      
        //System.out.println("Debug AjoutA***" + (this.app != null));
                 //   Logger.getagsStrtLogger(AjouterArticleController.class.getName()).log(Level.SEVERE, null, this.app != null);

        Article a = new Article(htmlText, 0, titre, 3, Authenticator.getCurrentAuth().getUsername(), new Date(), new Date());
        a.setId(this.article.getId());
        System.out.println(article.getId());
        aS.modifier(a);
        
        
        
        
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/blog/lireArticle.fxml"));
        AnchorPane forInserting  = (AnchorPane) fxmlLoader.load();
        LireArticleController c = (LireArticleController) fxmlLoader.getController();
        this.article = a;
        c.setArticle(a);
        c.blogController = blogController;
        blogWidget.getChildren().setAll(forInserting);
        
    }

    void setArticle(Article article) {
        this.article = article;
    }

    void setEditorText(String texte) {
        editor.setHtmlText(texte);
    }

    void setTitreText(String titre) {
        this.titre.setText(titre);
    }
    
    
}
