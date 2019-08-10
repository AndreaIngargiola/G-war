package view;

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
 * Read file and Order file by score.
 */
public class ReadAndOrderFileByScore {

    private List<Integer> arrayPlayer;
    private static final int MAXPLAYER = 10;
    /**
     * Method.
     */
    public void readFileAndOrder() {
        try {
            File inputFile = new File("src/view/CharacterScores.xml"); 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            Node characters = doc.getFirstChild();

            doc.getDocumentElement().normalize();
            List<Integer> arrayScore = new ArrayList<>();


            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("character");  //Lista di character (ora sono tre) con i loro nodi figli

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);  //scorre uno per uno i nodi character
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    arrayScore.add(Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()));  //ho aggiunto score nell'array
                }
            }
            Collections.sort(arrayScore);
            Collections.reverse(arrayScore);

            for (int i = 0; i < arrayScore.size(); i++) {
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);  //scorre uno per uno i nodi character
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        if (Integer.parseInt(eElement.getElementsByTagName("score").item(0).getTextContent()) == arrayScore.get(i)) {
                            Node newNode = nNode.cloneNode(true);
                            characters.appendChild(newNode);
                            characters.removeChild(nNode);
                            break;
                        }
                    }
                }
            }
            nList = doc.getElementsByTagName("character");
            if (nList.getLength() > MAXPLAYER) {
                Node nNode = nList.item(MAXPLAYER);
                characters.removeChild(nNode);
                arrayScore.remove(MAXPLAYER);
                //this.arrayPlayer.addAll(arrayScore);
            }
            this.arrayPlayer = new ArrayList<>(arrayScore);
            arrayScore.removeAll(arrayScore);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);

            System.out.println("Done read and order file by score");

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

    /*public List<Integer> getArrayList(){
        return this.arrayPlayer;
    }*/
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
