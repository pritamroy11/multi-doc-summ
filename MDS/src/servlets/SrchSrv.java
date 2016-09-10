package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DocInfoAccess;
import dao.SearchDocAccess;
import dao.StopsTableAccess;
import geneticAlgo.*;

/**
 * Servlet implementation class Srchserv
 */
public class SrchSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	ArrayList docNo_Data = new ArrayList(); //Doc_no+intro+desc+feat+concl
	
	@SuppressWarnings("unchecked")
	ArrayList weight = new ArrayList();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/HTML");
		
		PrintWriter out = response.getWriter(); 
		
		String query;
		query = request.getParameter("query");
		
		if(query.isEmpty()){
			out.println("<font face=verdana color=red><h3> You cant leave QUERY field EMPTY!!! </h3></font>");
			out.println ("</br></br><a href='SearchPage.jsp'>Go Back</a>");
		}
		else if(query.startsWith(" ")){
			out.println("<font face=verdana color=red><h3> You cant start a QUERY with a blank space!!! </h3></font>");
			out.println ("</br></br><a href='SearchPage.jsp'>Go Back</a>");
		}
		else{
		String keys[] = new String[20];
		String fil_keys[] = new String [20];
		
		int key_no = 0;
		int last_space = 0;
		ArrayList GOD = new ArrayList();
		Double twoDArray[][] = new Double [100][100];
		
		String summary = new String();
		
		/*check*/
		for (int i = 0; i < query.length(); i++){
			if (query.substring(i,i+1).equals(" ")) {
				keys[key_no] = query.substring(last_space, i);
				key_no++;
				last_space = i+1;
			}
			if (i == query.length() - 1){//Solves the trailing space problem
				keys[key_no] = query.substring(last_space, i+1);
				key_no++;
			}
		}
		fil_keys = filterKeys (keys);//only the keys n no stop words 
		
		/*int i = 0;
		while (fil_keys[i] != null){
			System.out.println (fil_keys[i]);
			i++;
		}//For testing the filtered keys generated*/
		
		
		/*CHECKED TILL HERE: "CLEAN"*/
		
		GOD = generate2DArray (fil_keys);
		/*GOD AL has Doc_No+2D Array Values till godIndex,
		 * after that, it has each elem of the 2D Array,
		 * then the last element is the godIndex.
		 */
		
		/*Extracting the 2D Array from GOD*/
		if (GOD == null) {
			out.println("<font face=verdana color=red><h3> OOPS!!!  Your search does not match any of our documents.....");
			out.println("<centre><br><br>Sorry.... Summary not generated !!</centre></h3></font>");
			out.println ("</br></br><a href='SearchPage.jsp'>Go Back</a>");
		}
		else {
			int godIndex = Integer.parseInt(GOD.get(GOD.size()-1).toString()); 
			int aa = 0; int bb = 0;
			for (int z = godIndex; z < GOD.size()-2; z = z + 4){
				twoDArray[aa][bb] = Double.parseDouble(GOD.get(z).toString());
				twoDArray[aa][bb+1] = Double.parseDouble(GOD.get(z+1).toString());
				twoDArray[aa][bb+2] = Double.parseDouble(GOD.get(z+2).toString());
				twoDArray[aa][bb+3] = Double.parseDouble(GOD.get(z+3).toString());
				aa++;
			}
			
			/*Printing the o/p after Genetic Algo*/
			Double[] res = new Double[4];
			GenOpt ga = new GenOpt();
			res = ga.GenAlgo(twoDArray);
			/*for(int i=0;i<4;i++){
				out.println(res[i]+" ");
			}
			/*int aa1 = 0;
			int bb1 = 0;//Generating just one entry twoDArray[0][0]=0   DIAGNOSE
			while (twoDArray[aa1][bb1] != null){
				out.println("\n\n line number: " + (aa1+1) + "-> " + twoDArray[aa1][bb1] + "\t");
				out.print(twoDArray[aa1][bb1+1] + "\t");
				out.print(twoDArray[aa1][bb1+2] + "\t");
				out.print(twoDArray[aa1][bb1+3] + "\n");
				bb1 = 0;
				aa1++;
			}
			/*out.println(twoDArray[0][0]);
			out.println(twoDArray[0][1]);
			out.println(twoDArray[0][2]);
			out.println(twoDArray[0][3]);*/
			
			/*Reverse mapping needed*/
			/*CHECK FROM HERE*/
			/*CHECK*/ArrayList docNo_Part = genSummary (GOD, res);
		
			for (int ctr = 0; ctr < docNo_Part.size(); ctr = ctr + 2) {
				summary += /*BIG*/iDsReturn/*PROBLEM*/(Integer.parseInt(docNo_Part.get (ctr).toString()), Integer.parseInt(docNo_Part.get (ctr+1).toString()));
			}
		
			out.println("<font color='red' size='+2'><u><b>THE SUMMARY FOR YOUR QUERY IS:</b></u></font></br>");
		
			out.println ("<p>" + summary + "</p>" + "</br><a href='SearchPage.jsp'>Go Back</a>");
		}
		}
	}

	@SuppressWarnings("unchecked")
	/*CHECK*/private ArrayList genSummary(ArrayList GOD, Double[] res) {
		int godIndex = Integer.parseInt(GOD.get(GOD.size() - 1).toString());
		//Integer docNo [] = new Integer [4];
		//String summary = new String();
		ArrayList docNo_Part = new ArrayList();
		
		/*For Extracting the doc numbers*/
		for (int i = 0; i < 4; i++){
			double value = res[i];
			
			for (int j = i + 1; j < godIndex; j = j + 5){
				if (value == Double.parseDouble(GOD.get(j).toString())){
					//docNo[i] = Integer.parseInt(GOD.get(j - i - 1).toString());
					docNo_Part.add (Integer.parseInt(GOD.get(j - i - 1).toString()));
					docNo_Part.add (i);
					/*System.out.println("Doc: " + GOD.get(j - i - 1).toString());
					System.out.println("Part: " + i);*/
				}
			}
			
		}/*Doc numbers extracted*/
		
		/*Data extraction process*/
		/*for (int k = 0; k < 4; k++){
			for (int l = 0; l < docNo_Data.size(); l = l + 5) {
				if (docNo [k] == Integer.parseInt(docNo_Data.get(l).toString())){
					//summary = summary + " \n" + docNo_Data.get(l+k+1).toString();
				}
			}
		}/*Data extracted*/
		
		return docNo_Part;
	}

	private String[] filterKeys(String[] keys) {
		ArrayList<String> stops = new ArrayList<String>();//gets data from table
		int i = 0, j = 0;//for keys[] n fil_keys[] resp
		boolean flag = false;//checking whether in stops or not
		String fil_keys[] = new String[20];
		StopsTableAccess dao = new StopsTableAccess();
		
		try {
			dao.openDBConnection();
			stops = dao.getWords();
			while (keys[i] != null){
				for (Object o :  stops)
					if (o.toString().equalsIgnoreCase(keys[i])) {
						flag = true;
						break;
					}
				if (flag == false){
					fil_keys[j] = keys[i];
					j++;
				}
				i++;
				flag = false;						
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fil_keys;
	}
	
	@SuppressWarnings({ "unchecked" })
	private ArrayList generate2DArray(String[] filKeys) { //filKeys has keys with no stops
		SearchDocAccess dao = new SearchDocAccess();
		DocInfoAccess datDao = new DocInfoAccess();
		
		ArrayList key_docNo = new ArrayList(); //keywords+doc numbers
		ArrayList<Integer> doc_no = new ArrayList<Integer>(); //Unique doc_nos 
		/*Prev Declaration of docNo_Data*/
		
		//To get the doc_nos corresponding to the obtained keys
		try {
			dao.openDBConnection();
			key_docNo = dao.getDocs(filKeys);
			dao.closeDBConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		{
		if (key_docNo.isEmpty()){
			return null;
		}}
		//To get all the unique doc_nos generated above and store them in an AL
		for (int i = 0; i < key_docNo.size(); i=i+2){
			boolean flag = false;
			for (Object o : doc_no){
				if (Integer.parseInt(o.toString()) == Integer.parseInt(key_docNo.get(i+1).toString())){
					flag = true;
					break;
				}
			}
			if (flag == false){
				doc_no.add(Integer.parseInt(key_docNo.get(i+1).toString()));
			}
		}
		
		//To get the data of the doc_nos obtained above
		try {
			datDao.openDBConnection();
			docNo_Data = datDao.getDocData(doc_no);
			datDao.closeDBConnection();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//Calculation of tf (Here only the number of sentences is being counted
		int doc_index = -1; //To take the index of the doc which is to be processed.
		ArrayList tf = new ArrayList(); //key, doc number, number of sentences in document
		int lines = 0; //counts the number of lines in document
		
		for (int i = 0; i < key_docNo.size(); i = i + 2){
			String key = key_docNo.get(i).toString();
			int doc = Integer.parseInt(key_docNo.get(i+1).toString());
			doc_index = -1;	
			for (int  j = 0; j < docNo_Data.size(); j = j + 5) 
				if (doc == Integer.parseInt(docNo_Data.get(j).toString())){
					doc_index = j;
					break;
				}
			
			String whole_doc = docNo_Data.get(doc_index+1).toString() + docNo_Data.get(doc_index+2).toString() + docNo_Data.get(doc_index+3).toString() + docNo_Data.get(doc_index+4).toString();  //contains the whole document
			/*whole_doc.concat(docNo_Data.get(doc_index+1).toString());
			whole_doc.concat(docNo_Data.get(doc_index+2).toString());
			whole_doc.concat(docNo_Data.get(doc_index+3).toString());
			whole_doc.concat(docNo_Data.get(doc_index+4).toString());
			
			if ((i > 2)) { //So that at least one round of loop is done before comparison
				//assuming that the same keys are in consecutive locations 
				if (!(key_docNo.get(i-2).toString().equalsIgnoreCase(key))){ 
					for (int k = 0; k < whole_doc.length(); i++){
						if (whole_doc.charAt(k) == '.')
							lines++;
					}
					tf.add(tf.size(), lines); //update the value of lines for the last keyword
				}
				else {*/
					lines = 0;
					for (int k = 0; k < whole_doc.length(); k++){
						if (whole_doc.charAt(k) == '.')
							lines++;
					}
					tf.add(key);	// a new keyword
					tf.add(Integer.parseInt(docNo_Data.get(doc_index).toString()));	//corresponding document number
					tf.add(lines); //add corresponding lines value
				
			
		}
		//tf obtained as an array list
		
		//Calculation of weight of term in a document
		/*Original declaration of weight*/
		
		for (int m = 0; m < tf.size(); m = m + 3) {
			String keyword = tf.get(m).toString();
			int C = 0;
			int N = 0;
			
			weight.add(keyword);
			weight.add(Integer.parseInt(tf.get(m+1).toString()));
			
			try {
				datDao.openDBConnection();
				N = datDao.countDocs();
				datDao.closeDBConnection();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			for (int n = 0; n < key_docNo.size(); n = n + 2){//provide for c to be initia\lised to 1
				if (key_docNo.get(n).toString().equals(keyword))
					C++;
			}
			
			double weight_int = Double.parseDouble(tf.get(m+2).toString()) * Math.log10(N) / C;
			weight.add(weight_int);
		}
		//Calculation of Weight completed. AL Weight = term + doc_number + weight 
		/*CHECKED TILL HERE: "CLEAN"*/
		
		//Calculation of Id(S) and Id(A)
		Double iDs [][] = new Double [4][100]; //Rows=docsXterms Columns=lines in doc
		Double iDa [][] = new Double [100][4]; //Rows=docsXterms Columns=4 (intro, desc, feat, concl)
		ArrayList docNo_iDa = new ArrayList();
		int godIndex = 0;
		
		for (int o = 0; o < key_docNo.size(); o = o + 2){ //Iterating on key+docNo AL
			//String key = key_docNo.get(o).toString();
			int docNo = Integer.parseInt(key_docNo.get(o+1).toString());
			int alIndex = -1;
			int wtIndex = -1;
			
			for (int p = 0; p < docNo_Data.size(); p = p + 5) //Iterating on docNo+Data AL
				if (docNo == Integer.parseInt(docNo_Data.get(p).toString())) {
					alIndex = p;
					break;
				}
			
			for (int q = 0; q < weight.size(); q = q + 3) //Iterating on weight
				if (docNo == Integer.parseInt(weight.get(q+1).toString())) {
					wtIndex = q;
					break;
				}
			
			docNo_iDa.add(docNo_Data.get(alIndex));
			godIndex++;
			
			for (int r = 0; r < 4; r++){ //Iterating to get the sentence by sentence value(s) required for ID(s)
				int noOfWords = 0;
				int noOfSen = 0;
				for (int s = 0; s < docNo_Data.get(alIndex+r+1).toString().length(); s++){ //For ID(s) of a part
					if (docNo_Data.get(alIndex+r+1).toString().charAt(s) == ' ')
						noOfWords++;
					
					if (docNo_Data.get(alIndex+r+1).toString().charAt(s) == '.') {
						noOfWords++;
						iDs [r][noOfSen] = Double.parseDouble(weight.get(wtIndex+2).toString())/noOfWords;
						noOfSen++;
						noOfWords = -1;
					}
				}
				
				iDa [o/2][r] = 0.0;
				for (int t = 0; t < noOfSen; t++)
					iDa [o/2][r] += iDs [r][t];
				
				docNo_iDa.add (iDa[o/2][r]);
				godIndex++;
			}			
		}
		
		int aa = 0;
		int bb = 0;
		
		while (iDa[aa][bb] != null) {
			docNo_iDa.add(iDa[aa][bb]);
			docNo_iDa.add(iDa[aa][bb+1]);
			docNo_iDa.add(iDa[aa][bb+2]);
			docNo_iDa.add(iDa[aa][bb+3]);
			aa++;
		}
		docNo_iDa.add (godIndex);
		
		return docNo_iDa;
	}
	
	/*BIG*/private String iDsReturn (int docNo, int part) {/*PROBLEM*/
		
		String retSent = new String();
		int alIndex = -1;
		int wtIndex = -1;
		Double iDs[] = new Double [100];
		
		for (int p = 0; p < docNo_Data.size(); p = p + 5) //Iterating on docNo+Data AL
			if (docNo == Integer.parseInt(docNo_Data.get(p).toString())) {
				alIndex = p;
				break;
			}
		
		for (int q = 0; q < weight.size(); q = q + 3) //Iterating on weight
			if (docNo == Integer.parseInt(weight.get(q+1).toString())) {
				wtIndex = q;
				break;
			}
		
		
		int noOfWords = 0;
		int noOfSen = 0;
		for (int s = 0; s < docNo_Data.get(alIndex+part+1).toString().length(); s++){ //For ID(s) of a part
			if (docNo_Data.get(alIndex+part+1).toString().charAt(s) == ' ')
				noOfWords++;
			
			if (docNo_Data.get(alIndex+part+1).toString().charAt(s) == '.') {
				noOfWords++;
				iDs [noOfSen] = Double.parseDouble(weight.get(wtIndex+2).toString())/noOfWords;
				//System.out.println("iDs for " + noOfSen + " is " + iDs[noOfSen]);
				noOfSen++;
				noOfWords = -1;
			}
		}
		/*iDs now populated*/
		
		/*Find the top two iDs values and their corresponding line numbers*/
		
		/*Maximum iDs*/
		double maxiDs = 0.0;
		int maxLine = -1;
		
		for (int t = 0; t < noOfSen; t++) {
			if (iDs[t] > maxiDs){
				maxiDs = iDs[t];
				maxLine = t;
			}
		}
		//System.out.println("Max iDs: " + maxiDs + " and Line " + maxLine);
		/*Second highest iDs*/
		double max2iDs = 0.0;
		int max2Line = -1;
		
		for (int u = 0; u < noOfSen; u++) {
			if ((iDs[u] > max2iDs) && (maxLine != u)){
				max2iDs = iDs[u];
				max2Line = u;
			}
		}
		//System.out.println("Max2 iDs: " + max2iDs + " and Line " + max2Line);
		/*Now extract the maximum iDs sentence*/
		int line = -1;
		for (int v = 0; v < docNo_Data.get(alIndex+part+1).toString().length(); v++){
			if (docNo_Data.get(alIndex+part+1).toString().charAt(v) == '.') {
				line++; v++;
			}
			if (line == maxLine -1){
				retSent += docNo_Data.get(alIndex+part+1).toString().charAt(v);
			}
		}
		retSent += ". ";
		
		//System.out.println(retSent);
		/*Now extract the maximum iDs sentence*/
		line = -1;
		for (int w = 0; w < docNo_Data.get(alIndex+part+1).toString().length(); w++){
			if (docNo_Data.get(alIndex+part+1).toString().charAt(w) == '.') {
				line++; w++;
			}
			if (line == max2Line -1){
				retSent += docNo_Data.get(alIndex+part+1).toString().charAt(w);
			}
		}
		retSent += ". ";
		
		return retSent;
	}
}
