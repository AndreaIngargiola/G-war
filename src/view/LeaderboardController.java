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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.PlayerLeaderboard;

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

    /**
     * Method to initialize any controls.
     */
    @FXML
    public void initialize() {
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
     *         the IOException.
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
