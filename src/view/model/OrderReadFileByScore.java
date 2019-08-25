package view.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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

/**
 * Read file and order file by score.
 */
public class OrderReadFileByScore {

    private List<Integer> arrayPlayer;
    private static final int MAXPLAYER = 10;

    /**
     * Method to read and order file.
     */
    public void readFileAndOrder() {
        try {
            final File inputFile = new File("src/view/CharacterScores.xml"); 
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final Document doc = docBuilder.parse(inputFile);
            final Node characters = doc.getFirstChild();

            final List<Integer> arrayScore = new ArrayList<>();

            NodeList nList = doc.getElementsByTagName("character");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                final Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element eElement = (Element) nNode;
                    arrayScore.add(Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()));
                }
            }
            Collections.sort(arrayScore);
            Collections.reverse(arrayScore);

            for (int i = 0; i < arrayScore.size(); i++) {
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    final Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        final Element eElement = (Element) nNode;
                        if (Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()) == arrayScore.get(i)) {
                            final Node newNode = nNode.cloneNode(true);
                            characters.appendChild(newNode);
                            characters.removeChild(nNode);
                            break;
                        }
                    }
                }
            }
            nList = doc.getElementsByTagName("character");
            if (nList.getLength() > MAXPLAYER) {
                final Node nNode = nList.item(MAXPLAYER);
                characters.removeChild(nNode);
                arrayScore.remove(MAXPLAYER);
            }
            this.arrayPlayer = new ArrayList<>(arrayScore);
            arrayScore.removeAll(arrayScore);


            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
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
     * Method to know the number of player.
     * @return
     *         the number of Player.
     */
    public Integer getNumberPlayerInLeaderboard() {
        return this.arrayPlayer.size();
    }

    /**
     * Method to know the score of the last player.
     * @return
     *         the last score.
     */
    public Integer getLastScore() {
        return this.arrayPlayer.get(this.getNumberPlayerInLeaderboard() - 1);
    }
}
