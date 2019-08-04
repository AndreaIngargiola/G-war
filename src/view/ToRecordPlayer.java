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
import org.xml.sax.SAXException;

/**
 * Class for add a player in loaderboard.
 */
public class ToRecordPlayer {
    /**
     * main.
     * @param argv
     *         the arg of main.
     */
    public static void main(final String[] argv) {
        String playerName = "Lorenzo";
        int scorePlayer = 2500;

        addRecord(playerName, scorePlayer);
    }

    private static void addRecord(final String playerName, final int scorePlayer) {
        try {
            File inputFile = new File("src/view/CharacterScores.xml"); 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            Node characters = doc.getFirstChild();
            //NodeList character = doc.getElementsByTagName("character");  //primo charachter, quindi prende solo i nodi figli del primo

            Node newNode = doc.createElement("character");

            characters.appendChild(newNode);
            Node lastCharacter = characters.getLastChild();
            Element player = doc.createElement("player");
            player.appendChild(doc.createTextNode(playerName));
            lastCharacter.appendChild(player);

            Element score = doc.createElement("score");
            score.appendChild(doc.createTextNode(String.valueOf(scorePlayer)));
            lastCharacter.appendChild(score);
            //NamedNodeMap attr = character.getAttributes();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);

            System.out.println("Done write (add node as last node)");

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

}
