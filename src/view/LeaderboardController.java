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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.PlayerLeaderboard;

/**
 * Controller of Leaderboard view (and add the information of player in the Leaderboard).
 */
public class LeaderboardController {

    @FXML
    private Button exitBtn;
    @FXML
    private TableView<PlayerLeaderboard> playerTable;
    @FXML
    private TableColumn<PlayerLeaderboard, String> playerNameColumn;
    @FXML
    private TableColumn<PlayerLeaderboard, Integer> playerScoreColumn;


    private MainMenuGame mainMenuGame;

    /**
     * create list and add some sample data.
     * @return
     *         the list of player.
     */
    /*public ObservableList<PlayerLeaderboard> getPlayerList() {
        ObservableList<PlayerLeaderboard> playerList = FXCollections.observableArrayList();
        playerList.add(new PlayerLeaderboard("E", 5000));
        playerList.add(new PlayerLeaderboard("A", 3000));
        playerList.add(new PlayerLeaderboard("V", 2000));
        return playerList;
    }*/

    @FXML
    private void initialize() {
        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
        playerScoreColumn.setCellValueFactory(cellData -> cellData.getValue().playerScoreProperty().asObject());
        ObservableList<PlayerLeaderboard> playerList = FXCollections.observableArrayList();
        playerTable.setItems(playerList);
        exitBtn.setVisible(true);


        try {
            File inputFile = new File("src/view/CharacterScores.xml"); 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            //Node characters = doc.getFirstChild();

            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("character");  //Lista di character (ora sono tre) con i loro nodi figli

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);  //scorre uno per uno i nodi character
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    playerList.add(new PlayerLeaderboard(eElement.getElementsByTagName("player").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent())));
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);

            System.out.println("Add the information of player in the TableView of Leaderboard");

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


    @FXML
    private void showMainMenu() {
        try {
            this.mainMenuGame.getMainMenu();
        } catch (Exception e) { }
    }

    @FXML
    private void exitL() {
        Platform.exit();
    }

    /**
     * Sets main menu.
     * @param mainMenu
     *         The main menu.
     */
    public void setMainMenuGame(final MainMenuGame mainMenu) {
        this.mainMenuGame = mainMenu;
    }
}
