package view.model;

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
 * Class for add a player in leaderboard.
 */
public class RecordScore {
    /**
     * Method for add player in leaderboard.
     * @param playerName
     *         the name of player.
     * @param scorePlayer
     *         the score of player.
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws TransformerException 
     */
    public void addRecord(final String playerName, final int scorePlayer) {
        try {
            final File inputFile = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "CharacterScores.xml"); 
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            if (inputFile.exists()) {
                final Document doc = docBuilder.parse(inputFile);
                final Node characters = doc.getFirstChild();

                final Node newNode = doc.createElement("character");

                characters.appendChild(newNode);
                final Node lastCharacter = characters.getLastChild();
                final Element player = doc.createElement("player");
                player.appendChild(doc.createTextNode(playerName));
                lastCharacter.appendChild(player);

                final Element score = doc.createElement("score");
                score.appendChild(doc.createTextNode(String.valueOf(scorePlayer)));
                lastCharacter.appendChild(score);

                final TransformerFactory trFactory = TransformerFactory.newInstance();
                final Transformer transformer = trFactory.newTransformer();
                final DOMSource source = new DOMSource(doc);
                final StreamResult result = new StreamResult(inputFile);
                transformer.transform(source, result);

            } else {
                final Document doc = docBuilder.newDocument();
                final Element rootElement = doc.createElement("characters");
                doc.appendChild(rootElement);

                final Element character = doc.createElement("character");
                rootElement.appendChild(character);
                final Element player = doc.createElement("player");
                player.appendChild(doc.createTextNode(playerName));
                character.appendChild(player);

                final Element score = doc.createElement("score");
                score.appendChild(doc.createTextNode(String.valueOf(scorePlayer)));
                character.appendChild(score);

                final TransformerFactory transformerFactory = TransformerFactory.newInstance();
                final Transformer transformer = transformerFactory.newTransformer();
                final DOMSource source = new DOMSource(doc);
                final StreamResult result = new StreamResult(inputFile);
                transformer.transform(source, result);
            }

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
