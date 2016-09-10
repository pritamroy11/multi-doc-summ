package geneticAlgo;

import java.util.Random;


public class GenOpt {
		
	final int populationSize = 100;
	Double[][] population = new Double[populationSize][4];
	Double[] result = new Double[4];
	final int iteration = 100;
	
	
	public Double[] GenAlgo(Double[][] input) {
		
		int r1,cnt=-1;
		
		int p=0;
		int q=0;
		while(input[p][q] != null){
			cnt++;
			p++;
		}
		
		if(cnt==0){ // if cond. satisfies only when there is only 1 doc matching to the given query
			for(int i=0;i<4;i++)
			result[i]=input[0][i];
		}
		else{
		for (int i=0;i<populationSize;i++){
			
			for (int j=0;j<4;j++){
			    
				r1 = new Random().nextInt(cnt)+0; // cnt is no of doc. related to the query...
				if(input[r1][j] <= 100) // infinite limit is set to 100
					population[i][j] = input[r1][j];
				else
					j--;
			}	
		}
		
		int iter = 1; // global iteration starts
		while(true){
		
		Double[] fit = Fitness(population); // calculate fitness values of populations

		//sorting fitnss values i.e. finding max & min finess to perform cross-over between them
		int m=0,n=0;
		Double max=fit[0];
		Double min=fit[0];
		for(int i=1;i<populationSize;i++){
			if(max<fit[i]){
				max=fit[i];
				m=i;
			}
			if(min>fit[i]){
				min=fit[i];
				n=i;
			}
		}
		
		//after completing all the global iteration the optimum solution
		if(iter==iteration){
			for(int i=0;i<4;i++){
				result[i]=population[m][i];
			}
			break;
		}
		
		// cross-over operation
		Double[] offspring1 = new Double[4];
		Double[] offspring2 = new Double[4];
		int ran1,ran2,crp,flag=0;
		crp = new Random().nextInt(10)+1; // crp is the cross-over rate i.e. 0.6
		if(crp<=3){ // single point cross-over
			for(int i=0;i<2;i++){
				ran1=m;
				ran2=n;
				offspring1[i] = population[ran1][i];
				offspring2[i] = population[ran2][i];
			}
			for(int i=2;i<4;i++){
				ran1=n;
				ran2=m;
				offspring1[i] = population[ran1][i];
				offspring2[i] = population[ran2][i];
			}
			flag=1;
		}
		if(crp>=8){ // double point cross-over
			offspring1[0] = population[m][0];
			offspring2[0] = population[n][0];
			for(int i=1;i<3;i++){
				offspring1[i] = population[n][i];
				offspring2[i] = population[m][i];
			}
			offspring1[3] = population[m][3];
			offspring2[3] = population[n][3];
			flag=1;
		}
		
		//survival of the offsprings
		if(flag==1){
			Double off1Fit = offspring1[0]+offspring1[1]+offspring1[2]+offspring1[3];
			Double off2Fit = offspring2[0]+offspring2[1]+offspring2[2]+offspring2[3];
			if(off1Fit>min && off1Fit>=off2Fit){
				for(int i=0;i<4;i++)
					population[n][i]=offspring1[i];
			}
			if(off2Fit>min && off2Fit>=off1Fit){
				for(int i=0;i<4;i++)
					population[n][i]=offspring2[i];
			}
		}
		iter++;
		}
		}
		
		return result;
		
	} 
	
	
	Double[] Fitness (Double[][] population){
		
		Double[] fit = new Double[populationSize];
		
		for(int i=0;i<populationSize;i++){
			fit[i] = population[i][0]+population[i][1]+population[i][2]+population[i][3];
		}
		
		return fit;
	}
	
}