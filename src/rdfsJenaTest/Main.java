package rdfsJenaTest;
// Les librairies
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import com.hp.hpl.jena.vocabulary.XSD;
import nu.xom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


public class Main {
    // Définition des espaces de nom
    static String nsGAME = "http://dijon.iut.fr/IUT/#";
    static String nsRDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static String nsRDFS = "http://www.w3.org/2000/01/rdf-schema#";

    // Définition des littéraux
    String Game = nsGAME + "Jeu";

    String Path = nsGAME + "CheminJeu";
    String Name = nsGAME + "NomJeu";
    String Desc = nsGAME + "DescriptionJeu";
    String Image = nsGAME + "ImageJeu";
    String Rating = nsGAME + "NoteJeu";
    String ReleaseDate = nsGAME + "DateSortieJeu";
    String Developer = nsGAME + "DeveloppeurJeu";
    String Publisher = nsGAME + "DistributeurJeu";
    String Genre = nsGAME + "GenreJeu";
    String Region = nsGAME + "RegionJeu";
    String RomType = nsGAME + "RomTypeJeu";


    String rdftype = nsRDF + "type";
    String rdfsclass = nsRDFS + "class";
    String rdfsrange = nsRDFS + "range";
    String rdfsdomain = nsRDFS + "domain";
    Model model;

    Main(){
        model = ModelFactory.createDefaultModel();
    }

    /**
     * charge le fichier
     * @throws ParsingException
     * @throws IOException
     * @return
     */
    public nu.xom.Document toXOM(String path) throws ParsingException, IOException, ParserConfigurationException, SAXException {

        File file = new File(path);
        nu.xom.Document doc = new Builder().build(file);

        return doc;
    }

    void initOntology()
    {

        //Définition des préfixes
        model.setNsPrefix("rdf", nsRDF);
        model.setNsPrefix("rdfs", nsRDFS);

        //Création du schéma

        //GAME
        Resource game = model.createResource(Game);
        model.add(game, RDF.type, RDFS.Class);

        Property path = model.createProperty(Path);
        model.add(path, RDFS.range,RDFS.Literal);
        model.add(path, RDFS.domain,Game);

        Property name = model.createProperty(Name);
        model.add(name, RDFS.range,RDFS.Literal);
        model.add(name, RDFS.domain,Game);

        Property desc = model.createProperty(Desc);
        model.add(desc, RDFS.range,RDFS.Literal);
        model.add(desc, RDFS.domain,Game);

        Property image = model.createProperty(Image);
        model.add(image, RDFS.range,RDFS.Literal);
        model.add(image, RDFS.domain,Game);

        Property rating = model.createProperty(Rating);
        model.add(rating, RDFS.range,RDFS.Literal);
        model.add(rating, RDFS.domain,Game);

        Property releaseDate = model.createProperty(ReleaseDate);
        model.add(releaseDate, RDFS.range,RDFS.Literal);
        model.add(releaseDate, RDFS.domain,Game);

        Property developer = model.createProperty(Developer);
        model.add(developer, RDFS.range,RDFS.Literal);
        model.add(developer, RDFS.domain,Game);

        Property publisher = model.createProperty(Publisher);
        model.add(publisher, RDFS.range,RDFS.Literal);
        model.add(publisher, RDFS.domain,Game);

        Property genre = model.createProperty(Genre);
        model.add(genre, RDFS.range,RDFS.Literal);
        model.add(genre, RDFS.domain,Game);

        Property region = model.createProperty(Region);
        model.add(region, RDFS.range,RDFS.Literal);
        model.add(region, RDFS.domain,Game);

        Property romType = model.createProperty(RomType);
        model.add(romType, RDFS.range,RDFS.Literal);
        model.add(romType, RDFS.domain,Game);


        //RECUPERATION DES DONNES DEPUIS FICHIERS XML


        try {
            Document doc = this.toXOM("./ressource/gb-gamelist.xml");
            Document doc1 = this.toXOM("./ressource/mastersystem-gamelist.xml");
            Document doc2 = this.toXOM("./ressource/nes-gamelist.xml");
            Element root = doc.getRootElement();


            for(Element elem: root.getChildElements()) {

                Resource jeu = model.createResource(nsGAME + elem.getAttributeValue("id"));

                jeu.addProperty(path ,elem.getFirstChildElement("path").getValue());
                jeu.addProperty(name ,elem.getFirstChildElement("name").getValue());
                if (elem.getFirstChildElement("desc") != null) {jeu.addProperty(desc , elem.getFirstChildElement("desc").getValue());}
                jeu.addProperty(image ,elem.getFirstChildElement("image").getValue());
                if (elem.getFirstChildElement("rating") != null) {jeu.addProperty(rating , elem.getFirstChildElement("rating").getValue());}
                if (elem.getFirstChildElement("rating") != null) {jeu.addProperty(releaseDate , elem.getFirstChildElement("releasedate").getValue());}
                if (elem.getFirstChildElement("developer") != null) {jeu.addProperty(developer , elem.getFirstChildElement("developer").getValue());}
                if (elem.getFirstChildElement("publisher") != null) {jeu.addProperty(publisher , elem.getFirstChildElement("publisher").getValue());}
                if (elem.getFirstChildElement("genre") != null) {jeu.addProperty(genre , elem.getFirstChildElement("genre").getValue());}
                jeu.addProperty(romType ,elem.getFirstChildElement("romtype").getValue());
            }

            for(Element elem: doc1.getRootElement().getChildElements()) {

                Resource jeu = model.createResource(nsGAME + elem.getAttributeValue("id"));

                jeu.addProperty(path ,elem.getFirstChildElement("path").getValue());
                jeu.addProperty(name ,elem.getFirstChildElement("name").getValue());
                if (elem.getFirstChildElement("desc") != null) {jeu.addProperty(desc , elem.getFirstChildElement("desc").getValue());}
                jeu.addProperty(image ,elem.getFirstChildElement("image").getValue());
                if (elem.getFirstChildElement("rating") != null) {jeu.addProperty(rating , elem.getFirstChildElement("rating").getValue());}
                if (elem.getFirstChildElement("rating") != null) {jeu.addProperty(releaseDate , elem.getFirstChildElement("releasedate").getValue());}
                if (elem.getFirstChildElement("developer") != null) {jeu.addProperty(developer , elem.getFirstChildElement("developer").getValue());}
                if (elem.getFirstChildElement("publisher") != null) {jeu.addProperty(publisher , elem.getFirstChildElement("publisher").getValue());}
                if (elem.getFirstChildElement("genre") != null) {jeu.addProperty(genre , elem.getFirstChildElement("genre").getValue());}
                jeu.addProperty(romType ,elem.getFirstChildElement("romtype").getValue());
            }
        } catch (ParsingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    void dumpOntology() {
        System.out.println("**************** Dump ***********************");
        // Liste des triples (statements)
        StmtIterator iter = model.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); // get next statement
            Resource subject = stmt.getSubject(); // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // Objet comme littÃ©ral
                System.out.print(" \"" + object.toString() + "\"");
                System.out.println(" .");
            }
        }
    }

    public void saveOntology(String nameFile) throws IOException
    {
        FileWriter fw = new FileWriter(new File(nameFile));
        //version RDF/XML
        model.write(fw, "RDF/XML-ABBREV");
    }

    public void onto() {

        //Namespace
        String NS = "http://iut.fr/";

        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

        //création des classes
        //enfant de Thing
        OntClass Person = m.createClass(NS + "Person");

        //enfants de Person
        OntClass Male = m.createClass(NS + "Male");
        OntClass Sibling = m.createClass(NS + "Sibling");
        OntClass Grandparents = m.createClass(NS + "Grandparents");
        OntClass Parent = m.createClass(NS + "Parent");
        OntClass Female = m.createClass(NS + "Female");
        OntClass Child = m.createClass(NS + "Child");


        //enfants de au dessus
        OntClass Uncle = m.createClass(NS + "Uncle");
        OntClass Brother = m.createClass(NS + "Brother");
        OntClass GrandFather = m.createClass(NS + "GrandFather");
        OntClass Father = m.createClass(NS + "Father");
        OntClass Sister = m.createClass(NS + "Sister");
        OntClass GrandMother = m.createClass(NS + "GrandMother");
        OntClass Mother = m.createClass(NS + "Mother");
        OntClass Son = m.createClass(NS + "Son");
        OntClass Daughter = m.createClass(NS + "Daughter");

        //hiérarchie
        //Person
        Sibling.setSuperClass(Person);
        Grandparents.setSuperClass(Person);
        Parent.setSuperClass(Person);
        Female.setSuperClass(Person);
        Male.setSuperClass(Person);
        Child.setSuperClass(Person);

        //enfants de Person
//        Uncle.setSuperClass(Male);
//        Brother.setSuperClass(Male);
//        Brother.setSuperClass(Sibling);
//        GrandFather.setSuperClass(Male);
//        GrandFather.setSuperClass(Grandparents);
//        Father.setSuperClass(Male);
//        Father.setSuperClass(Parent);
//        Sister.setSuperClass(Female);
//        Sister.setSuperClass(Sibling);
//        GrandMother.setSuperClass(Female);
//        GrandMother.setSuperClass(Grandparents);
//        Mother.setSuperClass(Female);
//        Mother.setSuperClass(Parent);
//        Son.setSuperClass(Child);
//        Daughter.setSuperClass(Child);

        Male.addSubClass(Uncle);
        Male.addSubClass(Brother);
        Sibling.addSubClass(Brother);
        Male.addSubClass(GrandFather);
        Grandparents.addSubClass(GrandFather);
        Male.addSubClass(Father);
        Parent.addSubClass(Father);
        Female.addSubClass(Sister);
        Sibling.addSubClass(Sister);
        Female.addSubClass(GrandMother);
        Grandparents.addSubClass(GrandMother);
        Female.addSubClass(Mother);
        Parent.addSubClass(Mother);
        Child.addSubClass(Son);
        Child.addSubClass(Daughter);

        //disjoint
        Male.setDisjointWith(Female);
        Father.setDisjointWith(Mother);
        Son.setDisjointWith(Daughter);
        GrandFather.setDisjointWith(GrandMother);

        //datatype
        DatatypeProperty name = m.createDatatypeProperty(NS + "Name");
        DatatypeProperty age = m.createDatatypeProperty(NS + "Age");
        DatatypeProperty nationality = m.createDatatypeProperty(NS + "Nationality");

        //domain
        name.addDomain(Person);
        age.addDomain(Person);
        nationality.addDomain(Person);
        //range
        name.setRange(XSD.xstring);
        age.setRange(XSD.xint);
        nationality.setRange(XSD.xstring);

        //property
        ObjectProperty isMarriedWith = m.createObjectProperty(NS + "isMarriedWith");
        ObjectProperty isParentOf = m.createObjectProperty(NS + "isParentOf");
        ObjectProperty isFatherOf = m.createObjectProperty(NS + "isFatherOf");
        ObjectProperty isMotherOf = m.createObjectProperty(NS + "isMotherOf");
        ObjectProperty isSiblingOf = m.createObjectProperty(NS + "isSiblingOf");
        ObjectProperty isBrotherOf = m.createObjectProperty(NS + "isBrotherOf");
        ObjectProperty isSisterOf = m.createObjectProperty(NS + "isSisterOf");
        ObjectProperty isChildOf = m.createObjectProperty(NS + "isChildOf");
        ObjectProperty isSonOf = m.createObjectProperty(NS + "isSonOf");
        ObjectProperty isDaughterOf = m.createObjectProperty(NS + "isDaughterOf");

        //domain
        isMarriedWith.addDomain(Person);
        isParentOf.addDomain(Person);
        isFatherOf.addDomain(Male);
        isMotherOf.addDomain(Female);
        isSiblingOf.addDomain(Person);
        isBrotherOf.addDomain(Male);
        isSisterOf.addDomain(Female);
        isChildOf.addDomain(Person);
        isSonOf.addDomain(Male);
        isDaughterOf.addDomain(Female);

        //range
        isMarriedWith.setRange(Person);
        isParentOf.setRange(Person);
        isFatherOf.setRange(Person);
        isMotherOf.setRange(Person);
        isSiblingOf.setRange(Person);
        isBrotherOf.setRange(Person);
        isSisterOf.setRange(Person);
        isChildOf.setRange(Person);
        isSonOf.setRange(Person);
        isDaughterOf.setRange(Person);

        //restricitons TODO
//        SomeValuesFromRestriction svfr;
//        MinCardinalityRestriction mcr = null;

        //types pour property
        isMarriedWith.convertToSymmetricProperty();
        isSiblingOf.convertToTransitiveProperty();
//        isChildOf.convertToInverseFunctionalProperty();
        name.convertToFunctionalProperty();
        age.convertToFunctionalProperty();
        nationality.convertToFunctionalProperty();

        try {
            m.write(new FileOutputStream("testUnion.owl"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

