package gui.profil;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import entites.Etablissement;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.EtablissementService;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javax.annotation.PostConstruct;

import static com.lynden.gmapsfx.javascript.object.MapTypeIdEnum.ROADMAP;
import static javafx.application.Application.launch;

import java.io.CharArrayReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListetablissementController implements MapComponentInitializedListener,Initializable {
    @FXML private Label lbl_oubFermer;
    @FXML private GoogleMapView mapView;
    GoogleMap map;
    @FXML private TableColumn<Etablissement, String> tablecol_desc;
    @FXML private TableColumn<Etablissement, String> tablecol_type;
    @FXML private Label lbl_nbexp;
    @FXML private Label lbl_nbRevue;
    @FXML private Label lbl_note;
    @FXML private Hyperlink hlink_visiter;
    @FXML private TableColumn<Etablissement, String> tablecol_horrairef;
    @FXML private TableColumn<Etablissement,Integer> tablecol_id;
    @FXML private TableColumn<Etablissement,String> tablecol_nom;
    @FXML private TableColumn<Etablissement,String> tablecol_adresse;
    @FXML private TableColumn<Etablissement,String> tablecol_gouvernorat;
    @FXML private TableColumn<Etablissement,String> tablecol_ville;
    @FXML private TableColumn<Etablissement,Double> tablecol_note;
    @FXML private TableColumn<Etablissement,String> tablecol_horraire;
    @FXML private TableColumn<Etablissement,Double> tablecol_long;
    @FXML private TableColumn<Etablissement,Double> tablecol_lati;
    @FXML private TableColumn<Etablissement,Boolean> tablecol_active;
    @FXML private TableColumn<Etablissement,String> tablecol_photo;
    @FXML private ImageView image_photoetab;
    @FXML private TableView<Etablissement> tableView_listetab;
    private Etablissement selectedEtab=new Etablissement();
    private Main app;
    private String  savedGouvernorat;
    private String savedVille;
    @PostConstruct
    public  void saveSearchCriteria(){
        System.out.println("ihavepost");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        EtablissementService etabServ=new EtablissementService();
        ArrayList<Etablissement> dataToshow=new ArrayList<>();
        System.out.println("savedGouvernorat"+savedGouvernorat);System.out.println("ssavedVille"+savedVille);
        if((savedGouvernorat!=""&&savedVille!=""))
        dataToshow= etabServ.selectByGouvernorat(savedGouvernorat);
        if((savedGouvernorat!="")&&(savedVille==""))
            dataToshow=etabServ.selectByGouvernorat(savedGouvernorat);
        System.out.println(dataToshow);

        // TODO :warring jusqu'a mnt les établissements ne sont pas filtrés par les gouvernorats et les villes etablissemnts to filter
        ObservableList<Etablissement> etablissements= FXCollections.observableList(dataToshow);//Selon le filtre
        tablecol_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tablecol_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tablecol_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tablecol_gouvernorat.setCellValueFactory(new PropertyValueFactory<>("Gouvernorat"));
        tablecol_ville.setCellValueFactory(new PropertyValueFactory<>("Ville"));
        tablecol_note.setCellValueFactory(new PropertyValueFactory<>("Note"));
        tablecol_horraire.setCellValueFactory(new PropertyValueFactory<>("Horraire"));
        tablecol_long.setCellValueFactory(new PropertyValueFactory<>("Longitude"));
        tablecol_lati.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        tablecol_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tablecol_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tablecol_active.setCellValueFactory(new PropertyValueFactory<>("estActive"));
        tablecol_photo.setCellValueFactory(new PropertyValueFactory<>("Photo"));
        tablecol_horrairef.setCellValueFactory(new PropertyValueFactory<>("horraire_f"));
        tableView_listetab.setItems(etablissements);
        Etablissement firstSelected=new Etablissement();
        tableView_listetab.getSelectionModel().selectFirst();
        firstSelected=tableView_listetab.getSelectionModel().getSelectedItem();
        //lbl_nbexp.setText(String.valueOf(etabServ.nbExperiences(firstSelected.getId())));
        //lbl_nbRevue.setText(String.valueOf(etabServ.nbRevues(firstSelected.getId())));
        //lbl_note.setText(firstSelected.getNote().toString());
    }
    public ArrayList<Etablissement> returnFiltredStreamByGouv(){
        EtablissementService etablissementService=new EtablissementService();
        return etablissementService.selectByGouvernorat(savedGouvernorat);
    }
    public ArrayList<Etablissement> returnFiltredStreamByVille(){
        EtablissementService etablissementService=new EtablissementService();
        return etablissementService.selectByVille(savedVille);
    }


    public void saveSearchInfo(String gouvFromContainer,String villeFromContainer){
        System.out.println("in saveSearchInfo():"+gouvFromContainer+villeFromContainer);
        this.savedGouvernorat=gouvFromContainer;
        this.savedVille=villeFromContainer;
    }
    public void setApp(Main app) {
        this.app = app;
    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                .mapType(ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        //Add a marker to the map
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position( new LatLong(47.6, -122.3) )
                .visible(Boolean.TRUE)
                .title("My Marker");

        Marker marker = new Marker( markerOptions );

        map.addMarker(marker);
    }
    @FXML
    public void takeSelectedEtab(MouseEvent mouseEvent) {
        selectedEtab = tableView_listetab.getSelectionModel().getSelectedItem();
        EtablissementService etabServ=new EtablissementService();
        lbl_nbexp.setText(String.valueOf(etabServ.nbExperiences(selectedEtab.getId())));
        lbl_nbRevue.setText(String.valueOf(etabServ.nbRevues(selectedEtab.getId())));
        lbl_note.setText(selectedEtab.getNote().toString());
    }
    @FXML
    public void gotoEtablissentProfile(ActionEvent actionEvent) {
    }

}
