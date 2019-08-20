package view;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import view.model.PlayerLeaderboard;

/**
 * Controller of Leaderboard view, add the information of player in the Leaderboard.
 */
public class LeaderboardController extends ViewControllerImpl {

    @FXML
    private Button exitBtn;
    @FXML
    private TableView<PlayerLeaderboard> playerTable;
    @FXML
    private TableColumn<PlayerLeaderboard, String> playerNameColumn;
    @FXML
    private TableColumn<PlayerLeaderboard, Integer> playerScoreColumn;
    @FXML
    private VBox primaryPane;
    @FXML
    private Label titleLeaderboard;
    @FXML
    private Button menuButton;
    @FXML
    private VBox secondPane;

    private static final double STAGE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final double STAGE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double TOP_PANE = STAGE_HEIGHT / 8;
    private static final double TITLE_WIDTH = (STAGE_WIDTH * 3) / 5;
    private static final double TITLE_HEIGHT = (STAGE_HEIGHT * 3) / 16;
    private static final double SEC_PANE_HEIGHT = (STAGE_HEIGHT * 3) / 8;
    private static final double TABLE_HEIGHT = STAGE_HEIGHT / 4;
    private static final double BOTTOM_SECOND_PANE = STAGE_HEIGHT / 16;
    private static final double TOP_SECOND_PANE = STAGE_HEIGHT / 2;
    private static final double BUTTON_WIDTH = TITLE_WIDTH / 3;
    private static final double BUTTON_HEIGHT = TITLE_HEIGHT / 3;
    private static final double TABLE_WIDTH = (STAGE_WIDTH * 3) / 10;
    private static final double CLM_TABLE_WIDTH = (STAGE_WIDTH * 3) / 20;

    /**
     * Method to initialize any controls.
     */
    @FXML
    public void initialize() {
        final BackgroundFill bg = new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY);
        final Background myBg = new Background(bg);
        this.primaryPane.setBackground(myBg);
        this.primaryPane.setMinSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.primaryPane.setMaxSize(STAGE_WIDTH, STAGE_WIDTH);
        this.secondPane.setMinSize(TITLE_WIDTH, SEC_PANE_HEIGHT);
        this.secondPane.setMaxSize(TITLE_WIDTH, SEC_PANE_HEIGHT);
        this.secondPane.setPadding(new Insets(TOP_SECOND_PANE, 0, 0, BOTTOM_SECOND_PANE));
        this.playerTable.setMinSize(TABLE_WIDTH, TABLE_HEIGHT);
        this.playerTable.setMaxSize(TABLE_WIDTH, TABLE_HEIGHT);
        this.playerNameColumn.setMinWidth(CLM_TABLE_WIDTH);
        this.playerScoreColumn.setMinWidth(CLM_TABLE_WIDTH);
        this.playerNameColumn.setMaxWidth(CLM_TABLE_WIDTH);
        this.playerScoreColumn.setMaxWidth(CLM_TABLE_WIDTH);

        this.titleLeaderboard.setGraphic(new ImageView(new Image("/leaderboard.png", TITLE_WIDTH, TITLE_HEIGHT, false, false)));
        this.titleLeaderboard.setPadding(new Insets(TOP_PANE, 0, 0, 0));
        this.titleLeaderboard.setMinSize(TITLE_WIDTH, SEC_PANE_HEIGHT);
        this.titleLeaderboard.setMaxSize(TITLE_WIDTH, SEC_PANE_HEIGHT);
        this.menuButton.setBackground(myBg);
        this.menuButton.setGraphic(new ImageView(new Image("/menuButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));
        this.exitBtn.setBackground(myBg);
        this.exitBtn.setGraphic(new ImageView(new Image("/quitButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));

        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
        playerScoreColumn.setCellValueFactory(cellData -> cellData.getValue().playerScoreProperty().asObject());
        final ObservableList<PlayerLeaderboard> playerList = FXCollections.observableArrayList();
        playerTable.setItems(playerList);

        try {
            final File inputFile = new File("src/view/CharacterScores.xml"); 
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final Document doc = docBuilder.parse(inputFile);
            final NodeList nList = doc.getElementsByTagName("character");

            for (int temp = 0; temp < nList.getLength(); temp++) {
            final Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element eElement = (Element) nNode;
                    playerList.add(new PlayerLeaderboard(eElement.getElementsByTagName("player").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent())));
                }
            }

            final TransformerFactory trFactory = TransformerFactory.newInstance();
            final Transformer transformer = trFactory.newTransformer();
            final DOMSource source = new DOMSource(doc);
            final StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    /**
     * Method to show main menu.
     * @throws IOException
     *         the exception.
     */
    @FXML
    protected void showMainMenu() throws IOException {
        this.getView().setViewState(ViewState.MAIN_MENU, null);
    }

    /**
     * Method to exit from game.
     */
    @FXML
    protected void exitL() {
        this.getController().closeApplication();
    }
}
