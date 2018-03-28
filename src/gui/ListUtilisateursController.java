package gui;

import entites.Utilisateur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import services.UtilisateurService;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ListUtilisateursController implements Initializable{

    @FXML private AnchorPane anchorpane_global;
    @FXML private TableView<Utilisateur> tabview_users;
    @FXML private TableColumn<Utilisateur,Integer> tabcol_id;
    @FXML private TableColumn<Utilisateur,String> tabcol_photo;
    @FXML private TableColumn<Utilisateur,Double> tabcol_lang;
    @FXML private TableColumn<Utilisateur,Double>  tabcol_lat;
    @FXML private TableColumn<Utilisateur,Integer> tabcol_idetab;
    @FXML private TableColumn<Utilisateur,String> tabcol_nom;
    @FXML private TableColumn<Utilisateur,String> tabcol_prenom;
    @FXML private TableColumn<Utilisateur,String> tabcol_email;
    @FXML private TableColumn<Utilisateur,Date> tabcol_lastlogin;
    @FXML private TableColumn<Utilisateur,String>  tabcol_role;

    private Main app;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtilisateurService userServ=new UtilisateurService();
        ObservableList<Utilisateur> utilisateurs= FXCollections.observableList(userServ.selectAll());
        tabcol_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabcol_photo.setCellValueFactory(new PropertyValueFactory<>("photoProfil"));
        tabcol_lang.setCellValueFactory(new PropertyValueFactory<>("langitude"));
        tabcol_lat.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        tabcol_idetab.setCellValueFactory(new PropertyValueFactory<>("username"));
        tabcol_nom.setCellValueFactory(new PropertyValueFactory<>("username"));
        tabcol_prenom.setCellValueFactory(new PropertyValueFactory<>("usernameCanonical"));
        tabcol_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tabcol_lastlogin.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
        tabcol_role.setCellValueFactory(new PropertyValueFactory<>("roles"));
        tabview_users.setItems(utilisateurs);
    }
    public void setApp(Main app) {
        this.app = app;
    }
}