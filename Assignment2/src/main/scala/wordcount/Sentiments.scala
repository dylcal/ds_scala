package wordcount

import java.awt.{Color, GridLayout}

import org.jfree.chart.{ChartPanel, JFreeChart}
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYDotRenderer
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}
import org.jfree.ui.ApplicationFrame
import org.jfree.util.ShapeUtilities


/**
  * @author hendrik
  * modified by akarakochev
  */
class Sentiments(sentiFile: String) {

  val sentiments: Map[String, Int] = getSentiments(sentiFile)

  val proc = new Processing()

  /** ********************************************************************************************
    *
    * Aufgabe 5
    *
    * ********************************************************************************************
    */

  def getDocumentGroupedByCounts(filename: String, wordCount: Int): List[(Int, List[String])] = {
    val processedFile = Processing.getData(filename) //map
    println("FILE: " + processedFile)
    //List((0,Call me Ishmael. Some years ago--never mind how long precisely--having), (1,little or no money in my purse, and nothing particular to interest me on), ....
    processedFile.flatMap( //Aus der Mappe nur Wörter extrahieren
      elem =>
      proc.getWords(elem._2)) //0, (call, me, Ishmael, some, ..)
      .grouped(wordCount) //Wörter nach wordCount gruppieren
      .zipWithIndex //der neuen Liste wird ein Index angehangen
      .map{
          x => {
            println("X: " + x) //wordcount=5 --> (List(call, me, ishmael, some, years),0)
            (x._2+1, x._1) //(List(1, (call, me, ishmael, some, years))
          }
      }.toList

  }

  def getDocumentSplitByPredicate(filename: String, predicate:String=>Boolean): List[(Int, List[String])] = ???
  /*{
    val processedFile = Processing.getData(filename) //map
    processedFile.flatMap( //Aus der Mappe nur Wörter extrahieren
      elem =>
        proc.getWords(elem._2))
        .partition(x => predicate(x)) //???????
      ?????
  }
*/

  /**
    * Diese bekommt eine  Liste von  Abschnitten  (Abschnittnr, Liste   von   Wörtern)   und
    * errechnet   den jeweiligen Sentimentwert. Der Sentimentwert wird als der Durchschnitt der
    * Sentimentwerte aller bekannten Wörtern in dem Abschnitt berechnet. Ergebnis ist ein Tripel,
    * bestehend aus  der Abschnittsnummer,  dem Sentimentwert  und  der  relativen  Anzahl  von  Wörtern,
    * die für die Sentimentanalyse verwendet werden konnten.
    *
    * @param l - List((1,List(chapter, loomings, call, me, ishmael, some, years, ago, never, mind, how, long, precisely, having, little, or, no, money, in, my, purse, and, nothing, particular, to, interest, me, on, shore, i, thought, i, would, sail, about, a, little, and, see, the, watery, part, of, the, world, it, is, a, way, i, have, of, driving, off, the, spleen, and, regulating, the, circulation, whenever, i, find, myself, growing, grim, about, the, mouth, whenever, it, is, a, damp, drizzly, november, in, my, soul, whenever, i, find, myself, involuntarily, pausing, before, coffin, warehouses, and, bringing, up, the, rear, of, every, funeral, i, meet, and, especially, whenever, my, hypos, get, such, an, upper, hand, of, me, that, it, requires, a, strong, moral, principle, to, prevent, me, from, deliberately, stepping, into, the, street, and, methodically, knocking, people, s, hats, off, then, i, account, it, high, time, to, get, to, sea, as, soon, as, i, can, this, is, my, substitute, for, pistol, and, ball, with, a, philosophical, flourish, cato, throws, himself, upon, his, sword, i, quietly, take, to, the, ship, there, is, nothing, surprising, in, this, if, they, but, knew, it, almost, all, men, in, their, degree, some, time, or, other, cherish, very, nearly, the, same, feelings, towards)), (2,List(the, ocean, with, me, there, now, is, your, insular, city, of, the, manhattoes, belted, round, by, wharves, as, indian, isles, by, coral, reefs, commerce, surrounds, it, with, her, surf, right, and, left, the, streets, take, you, waterward, its, extreme, downtown, is, the, battery, where, that, noble, mole, is, washed, by, waves, and, cooled, by, breezes, which, a, few, hours, previous, were, out, of, sight, of, land, look, at, the, crowds, of, water, gazers, there, circumambulate, the, city, of, a, dreamy, sabbath, afternoon, go, from, corlears, hook, to, coenties, slip, and, from, thence, by, whitehall, northward, what, do, you, see, posted, like, silent, sentinels, all, around, the, town, stand, thousands, upon, thousands, of, mortal, men, fixed, in, ocean, reveries, some, leaning, against, the, spiles, some, seated, upon, the, pier, heads, some, looking, over, the, bulwarks, of, ships, from, china, some, high, aloft, in, the, rigging, as, if, striving, to, get, a, still, better, seaward, peep, but, these, are, all, landsmen, of, week, days, pent, up, in, lath, and, plaster, tied, to, counters, nailed, to, benches, clinched, to, desks, how, then, is, this, are, the, green, fields, gone, what, do, they, here, but, look, here, come, more, crowds, pacing, straight, for, the)), (3,List(water, and, seemingly, bound, for, a, dive, strange, nothing, will, content, them, but, the, extremest, limit, of, the, land, loitering, under, the, shady, lee, of, yonder, warehouses, will, not, suffice, no, they, must, get, just, as, nigh, the, water, as, they, possibly, can, without, falling, in, and, there, they, stand, miles, of, them, leagues, inlanders, all, they, come, from, lanes, and, alleys, streets, and, avenues, north, east, south, and, west, yet, here, they, all, unite, tell, me, does, the, magnetic, virtue, of, the, needles, of, the, compasses, of, all, those, ships, attract, them, thither, once, more, say, you, are, in, the, country, in, some, high, land, of, lakes, take, almost, any, path, you, please, and, ten, to, one, it, carries, you, down, in, a, dale, and, leaves, you, there, by, a, pool, in, the, stream, there, is, magic, in, it, let, the, most, absent, minded, of, men, be, plunged, in, his, deepest, reveries, stand, that, man, on, his, legs, set, his, feet, a, going, and, he, will, infallibly, lead, you, to, water, if, water, there, be, in, all, that, region, should, you, ever, be, athirst, in, the, great, american, desert, try, this, experiment, if, your, caravan, happen, to, be, supplied)), (4,List(with, a, metaphysical, professor, yes, as, every, one, knows, meditation, and, water, are, wedded, for, ever, but, here, is, an, artist, he, desires, to, paint, you, the, dreamiest, shadiest, quietest, most, enchanting, bit, of, romantic, landscape, in, all, the, valley, of, the, saco, what, is, the, chief, element, he, employs, there, stand, his, trees, each, with, a, hollow, trunk, as, if, a, hermit, and, a, crucifix, were, within, and, here, sleeps, his, meadow, and, there, sleep, his, cattle, and, up, from, yonder, cottage, goes, a, sleepy, smoke, deep, into, distant, woodlands, winds, a, mazy, way, reaching, to, overlapping, spurs, of, mountains, bathed, in, their, hill, side, blue, but, though, the, picture, lies, thus, tranced, and, though, this, pine, tree, shakes, down, its, sighs, like, leaves, upon, this, shepherd, s, head, yet, all, were, vain, unless, the, shepherd, s, eye, were, fixed, upon, the, magic, stream, before, him, go, visit, the, prairies, in, june, when, for, scores, on, scores, of, miles, you, wade, knee, deep, among, tiger, lilies, what, is, the, one, charm, wanting, water, there, is, not, a, drop, of, water, there, were, niagara, but, a, cataract, of, sand, would, you, travel, your, thousand, miles, to, see, it, why, did)), (5,List(the, poor, poet, of, tennessee, upon, suddenly, receiving, two, handfuls, of, silver, deliberate, whether, to, buy, him, a, coat, which, he, sadly, needed, or, invest, his, money, in, a, pedestrian, trip, to, rockaway, beach, why, is, almost, every, robust, healthy, boy, with, a, robust, healthy, soul, in, him, at, some, time, or, other, crazy, to, go, to, sea, why, upon, your, first, voyage, as, a, passenger, did, you, yourself, feel, such, a, mystical, vibration, when, first, told, that, you, and, your, ship, were, now, out, of, sight, of, land, why, did, the, old, persians, hold, the, sea, holy, why, did, the, greeks, give, it, a, separate, deity, and, own, brother, of, jove, surely, all, this, is, not, without, meaning, and, still, deeper, the, meaning, of, that, story, of, narcissus, who, because, he, could, not, grasp, the, tormenting, mild, image, he, saw, in, the, fountain, plunged, into, it, and, was, drowned, but, that, same, image, we, ourselves, see, in, all, rivers, and, oceans, it, is, the, image, of, the, ungraspable, phantom, of, life, and, this, is, the, key, to, it, all, now, when, i, say, that, i, am, in, the, habit, of, going, to, sea, whenever, i, begin, to, grow, hazy)), (6,List(about, the, eyes, and, begin, to, be, over, conscious, of, my, lungs, i, do, not, mean, to, have, it, inferred, that, i, ever, go, to, sea, as, a, passenger, for, to, go, as, a, passenger, you, must, needs, have, a, purse, and, a, purse, is, but, a, rag, unless, you, have, something, in, it, besides, passengers, get, sea, sick, grow, quarrelsome, don, t, sleep, of, nights, do, not, enjoy, themselves, much, as, a, general, thing, no, i, never, go, as, a, passenger, nor, though, i, am, something, of, a, salt, do, i, ever, go, to, sea, as, a, commodore, or, a, captain, or, a, cook, i, abandon, the, glory, and, distinction, of, such, offices, to, those, who, like, them, for, my, part, i, abominate, all, honourable, respectable, toils, trials, and, tribulations, of, every, kind, whatsoever, it, is, quite, as, much, as, i, can, do, to, take, care, of, myself, without, taking, care, of, ships, barques, brigs, schooners, and, what, not, and, as, for, going, as, cook, though, i, confess, there, is, considerable, glory, in, that, a, cook, being, a, sort, of, officer, on, ship, board, yet, somehow, i, never, fancied, broiling, fowls, though, once, broiled, judiciously, buttered, and, judgmatically, salted)), (7,List(and, peppered, there, is, no, one, who, will, speak, more, respectfully, not, to, say, reverentially, of, a, broiled, fowl, than, i, will, it, is, out, of, the, idolatrous, dotings, of, the, old, egyptians, upon, broiled, ibis, and, roasted, river, horse, that, you, see, the, mummies, of, those, creatures, in, their, huge, bake, houses, the, pyramids, no, when, i, go, to, sea, i, go, as, a, simple, sailor, right, before, the, mast, plumb, down, into, the, forecastle, aloft, there, to, the, royal, mast, head, true, they, rather, order, me, about, some, and, make, me, jump, from, spar, to, spar, like, a, grasshopper, in, a, may, meadow, and, at, first, this, sort, of, thing, is, unpleasant, enough, it, touches, one, s, sense, of, honour, particularly, if, you, come, of, an, old, established, family, in, the, land, the, van, rensselaers, or, randolphs, or, hardicanutes, and, more, than, all, if, just, previous, to, putting, your, hand, into, the, tar, pot, you, have, been, lording, it, as, a, country, schoolmaster, making, the, tallest, boys, stand, in, awe, of, you, the, transition, is, a, keen, one, i, assure, you, from, a, schoolmaster, to, a, sailor, and, requires, a, strong, decoction, of, seneca, and, the, stoics, to)), (8,List(enable, you, to, grin, and, bear, it, but, even, this, wears, off, in, time, what, of, it, if, some, old, hunks, of, a, sea, captain, orders, me, to, get, a, broom, and, sweep, down, the, decks, what, does, that, indignity, amount, to, weighed, i, mean, in, the, scales, of, the, new, testament, do, you, think, the, archangel, gabriel, thinks, anything, the, less, of, me, because, i, promptly, and, respectfully, obey, that, old, hunks, in, that, particular, instance, who, ain, t, a, slave, tell, me, that, well, then, however, the, old, sea, captains, may, order, me, about, however, they, may, thump, and, punch, me, about, i, have, the, satisfaction, of, knowing, that, it, is, all, right, that, everybody, else, is, one, way, or, other, served, in, much, the, same, way, either, in, a, physical, or, metaphysical, point, of, view, that, is, and, so, the, universal, thump, is, passed, round, and, all, hands, should, rub, each, other, s, shoulder, blades, and, be, content, again, i, always, go, to, sea, as, a, sailor, because, they, make, a, point, of, paying, me, for, my, trouble, whereas, they, never, pay, passengers, a, single, penny, that, i, ever, heard, of, on, the, contrary, passengers, themselves, must)), (9,List(pay, and, there, is, all, the, difference, in, the, world, between, paying, and, being, paid, the, act, of, paying, is, perhaps, the, most, uncomfortable, infliction, that, the, two, orchard, thieves, entailed, upon, us, but, being, paid, what, will, compare, with, it, the, urbane, activity, with, which, a, man, receives, money, is, really, marvellous, considering, that, we, so, earnestly, believe, money, to, be, the, root, of, all, earthly, ills, and, that, on, no, account, can, a, monied, man, enter, heaven, ah, how, cheerfully, we, consign, ourselves, to, perdition, finally, i, always, go, to, sea, as, a, sailor, because, of, the, wholesome, exercise, and, pure, air, of, the, fore, castle, deck, for, as, in, this, world, head, winds, are, far, more, prevalent, than, winds, from, astern, that, is, if, you, never, violate, the, pythagorean, maxim, so, for, the, most, part, the, commodore, on, the, quarter, deck, gets, his, atmosphere, at, second, hand, from, the, sailors, on, the, forecastle, he, thinks, he, breathes, it, first, but, not, so, in, much, the, same, way, do, the, commonalty, lead, their, leaders, in, many, other, things, at, the, same, time, that, the, leaders, little, suspect, it, but, wherefore, it, was, that, after, having, repeatedly, smelt, the)), (10,List(sea, as, a, merchant, sailor, i, should, now, take, it, into, my, head, to, go, on, a, whaling, voyage, this, the, invisible, police, officer, of, the, fates, who, has, the, constant, surveillance, of, me, and, secretly, dogs, me, and, influences, me, in, some, unaccountable, way, he, can, better, answer, than, any, one, else, and, doubtless, my, going, on, this, whaling, voyage, formed, part, of, the, grand, programme, of, providence, that, was, drawn, up, a, long, time, ago, it, came, in, as, a, sort, of, brief, interlude, and, solo, between, more, extensive, performances, i, take, it, that, this, part, of, the, bill, must, have, run, something, like, this, grand, contested, election, for, the, presidency, of, the, united, states, whaling, voyage, by, one, ishmael, bloody, battle, in, affghanistan, though, i, cannot, tell, why, it, was, exactly, that, those, stage, managers, the, fates, put, me, down, for, this, shabby, part, of, a, whaling, voyage, when, others, were, set, down, for, magnificent, parts, in, high, tragedies, and, short, and, easy, parts, in, genteel, comedies, and, jolly, parts, in, farces, though, i, cannot, tell, why, this, was, exactly, yet, now, that, i, recall, all, the, circumstances, i, think, i, can, see, a, little, into, the)), (11,List(springs, and, motives, which, being, cunningly, presented, to, me, under, various, disguises, induced, me, to, set, about, performing, the, part, i, did, besides, cajoling, me, into, the, delusion, that, it, was, a, choice, resulting, from, my, own, unbiased, freewill, and, discriminating, judgment, chief, among, these, motives, was, the, overwhelming, idea, of, the, great, whale, himself, such, a, portentous, and, mysterious, monster, roused, all, my, curiosity, then, the, wild, and, distant, seas, where, he, rolled, his, island, bulk, the, undeliverable, nameless, perils, of, the, whale, these, with, all, the, attending, marvels, of, a, thousand, patagonian, sights, and, sounds, helped, to, sway, me, to, my, wish, with, other, men, perhaps, such, things, would, not, have, been, inducements, but, as, for, me, i, am, tormented, with, an, everlasting, itch, for, things, remote, i, love, to, sail, forbidden, seas, and, land, on, barbarous, coasts, not, ignoring, what, is, good, i, am, quick, to, perceive, a, horror, and, could, still, be, social, with, it, would, they, let, me, since, it, is, but, well, to, be, on, friendly, terms, with, all, the, inmates, of, the, place, one, lodges, in, by, reason, of, these, things, then, the, whaling, voyage, was, welcome, the, great, flood, gates, of, the)), (12,List(wonder, world, swung, open, and, in, the, wild, conceits, that, swayed, me, to, my, purpose, two, and, two, there, floated, into, my, inmost, soul, endless, processions, of, the, whale, and, mid, most, of, them, all, one, grand, hooded, phantom, like, a, snow, hill, in, the, air)))
    * @return
    */
  def analyseSentiments(l: List[(Int, List[String])]): List[(Int, Double, Double)] = {
    println("analyseSentiments")
    println("Verfuegbare Sentiments: " + sentiments.keySet.toList.sorted)
    println("\nINPUT l: \n" + l)

    //Erzeugt eine Liste, die Abschnitt, Sentimentwörter und gesamte Anzahl der Wörter enthält
    val sentiWordsAndValues = l.foldLeft(List[(Int, List[(String, Int)], Int)]())((list, paragraph) => {
      println("Aggregatsliste: " + list)
      println("Paragraph: " + paragraph)
      println("Paragraph 1 elem: " + paragraph._1)
      println("Paragraph 2 elem: " + paragraph._2 + "\n")
      //Listen konkateniert
      //paragraph._1 ist die Zeilennr
      //paragraph._2 ist die Liste von Wörtern
      //Auf dem 2. Element vom Paragraph wird iteriert (foldLeft) mit dem Ziel, Ergebnis daraus zu ziehen
      /*
      * val sentimentValues= words.flatMap(sentiments.get)
      * sentimentValues.sum
      * sentimentValues.length/ words.length.toDouble
      * */
      list++List((paragraph._1, paragraph._2.foldLeft(List[(String, Int)]())((list, word) =>
        sentiments.get(word) match{
          //Wort wird hingefügt, wenn es ein Sentimentwort ist
          case Some(elem) => List.concat(list, List((word, sentiments.get(word).last))) //NoSuchElementException
          //sonst nicht
          case None => list
        }), paragraph._2.length))
    })

    println("Verarbeitete Liste: " + sentiWordsAndValues + "\n")
   //Dieser Teil liefert korrektes Format zurück

    val result = sentiWordsAndValues.foldLeft(List[(Int, Double, Double)]())((list, paragraph) => {
      println("paragraph._2: " + paragraph._2 + ", Länge: " + paragraph._2.length.toDouble)
      //Example: paragraph._2 == List((no,-1), (interest,1), (growing,1), (funeral,-1), (strong,2), (prevent,-1), (cherish,2))
          // jede getrennte Liste mit Wörter und Sentiment-Wert

      //Über paragraph_2 iterieren
      //sentValue sind die Tupeln mit Wort und Sent: (no, -1), (interest, 1)
      //sentValueContainer ist zunächst eine leere Liste, die dann die verschiedene Sent-Values speichert
      val sentiment_values = paragraph._2.foldLeft(List[Int]())((sentValueContainer, tupelnWortSentValue) =>
        {
          println("TUPEL sentValue: " + tupelnWortSentValue)
          //sentValueContainer sieht durch die Iteration so aus: List(1, 1, -1)...
          //hier wird der Wert der leeren Liste (sentValueContainer) angehängt
          tupelnWortSentValue._2 :: sentValueContainer
        }).sum.toDouble / paragraph._2.length.toDouble //Berechnung ausführen anhand der Summe der Sentimente / Listengröße
      //Noch ein letzter schritt
      val rel_words_used = paragraph._2.length.toDouble / paragraph._3.toDouble
      println("Zusammenfassung des Ergebnisses: ")
      println("paragraph._1: " + paragraph._1 + " ")
      print("sentiment_values: " + sentiment_values + " ")
      print("Rel_words_used: " + rel_words_used + "\n")
      List.concat(list, List((paragraph._1, sentiment_values, rel_words_used)))
    })
    result
  }

  /** ********************************************************************************************
    *
    * Helper Functions
    *
    * ********************************************************************************************
    */

  def getSentiments(filename: String): Map[String, Int] = {
    val url = getClass.getResource("/" + filename).getPath
    val src = scala.io.Source.fromFile(url)
    val iter = src.getLines()
    val result: Map[String, Int] = (for (row <- iter) yield {
      val seg = row.split("\t"); (seg(0) -> seg(1).toInt)
    }).toMap
    src.close()
    result
  }

  def createGraph(data: List[(Int, Double, Double)], xlabel:String="Abschnitt", title:String="Sentiment-Analyse"): Unit = {

    //create xy series
    val sentimentsSeries: XYSeries = new XYSeries("Sentiment-Werte")
    data.foreach { case (i, sentimentValue, _) => sentimentsSeries.add(i, sentimentValue) }
    val relWordsSeries: XYSeries = new XYSeries("Relative Haeufigkeit der erkannten Worte")
    data.foreach { case (i, _, relWordsValue) => relWordsSeries.add(i, relWordsValue) }

    //create xy collections
    val sentimentsDataset: XYSeriesCollection = new XYSeriesCollection()
    sentimentsDataset.addSeries(sentimentsSeries)
    val relWordsDataset: XYSeriesCollection = new XYSeriesCollection()
    relWordsDataset.addSeries(relWordsSeries)

    //create renderers
    val relWordsDot: XYDotRenderer = new XYDotRenderer()
    relWordsDot.setDotHeight(5)
    relWordsDot.setDotWidth(5)
    relWordsDot.setSeriesShape(0, ShapeUtilities.createDiagonalCross(3, 1))
    relWordsDot.setSeriesPaint(0, Color.BLUE)

    val sentimentsDot: XYDotRenderer = new XYDotRenderer()
    sentimentsDot.setDotHeight(5)
    sentimentsDot.setDotWidth(5)

    //create xy axis
    val xax: NumberAxis = new NumberAxis(xlabel)
    val y1ax: NumberAxis = new NumberAxis("Sentiment Werte")
    val y2ax: NumberAxis = new NumberAxis("Relative Haeufigfkeit")

    //create plots
    val plot1: XYPlot = new XYPlot(sentimentsDataset, xax, y1ax, sentimentsDot)
    val plot2: XYPlot = new XYPlot(relWordsDataset, xax, y2ax, relWordsDot)

    val chart1: JFreeChart = new JFreeChart(plot1)
    val chart2: JFreeChart = new JFreeChart(plot2)
    val frame: ApplicationFrame = new ApplicationFrame(title)
    frame.setLayout(new GridLayout(2,1))

    val chartPanel1: ChartPanel = new ChartPanel(chart1)
    val chartPanel2: ChartPanel = new ChartPanel(chart2)

    frame.add(chartPanel1)
    frame.add(chartPanel2)
    frame.pack()
    frame.setVisible(true)
  }
}
