package polyEval;

import java.util.ArrayList;
import java.util.List;

public class Test
{
	
	public static void main(String[] args)
	{
		//testHorners();
		testFindBestEval();
	}
	
	public static void testHorners()
	{
		List<Monomial> monomials=new ArrayList<>();
		
		/*
		monomials.add(new Monomial(65, new int[]{2}));
		monomials.add(new Monomial(8, new int[]{1}));
		monomials.add(new Monomial(3, new int[]{0}));
		*/
		
		monomials.add(new Monomial(2, new int[]{6,4}));
		monomials.add(new Monomial(5, new int[]{2,3}));
		monomials.add(new Monomial(3, new int[]{4,1}));
		monomials.add(new Monomial(7, new int[]{3,0}));
		monomials.add(new Monomial(11, new int[]{2,1}));
		monomials.add(new Monomial(13, new int[]{0,3}));
		
		Polynomial poly=new Polynomial(monomials.toArray(new Monomial[0]));
		HornersMethodPolynomial hmp=poly.hornersMethodPolynomial(poly.monomials);
		System.out.println(hmp);
	}
	
	public static void testFindBestEval()
	{
		ArrayList<Monomial> monomials=new ArrayList<>();
		
		//degree 1
		/*
		monomials.add(new Monomial(2, new int[]{0,0}));
		monomials.add(new Monomial(2, new int[]{0,1}));
		monomials.add(new Monomial(2, new int[]{1,0}));
		*/
		
		//degree 2
		/*
		monomials.add(new Monomial(2, new int[]{0,0}));
		monomials.add(new Monomial(2, new int[]{0,1}));
		monomials.add(new Monomial(2, new int[]{0,2}));
		monomials.add(new Monomial(2, new int[]{1,0}));
		monomials.add(new Monomial(2, new int[]{1,1}));
		monomials.add(new Monomial(2, new int[]{2,0}));
		*/
		
		//degree 3
		/*
		monomials.add(new Monomial(2, new int[]{0,0}));
		monomials.add(new Monomial(2, new int[]{0,1}));
		monomials.add(new Monomial(2, new int[]{0,2}));
		monomials.add(new Monomial(2, new int[]{0,3}));
		monomials.add(new Monomial(2, new int[]{1,0}));
		monomials.add(new Monomial(2, new int[]{1,1}));
		monomials.add(new Monomial(2, new int[]{1,2}));
		monomials.add(new Monomial(2, new int[]{2,0}));
		monomials.add(new Monomial(2, new int[]{2,1}));
		monomials.add(new Monomial(2, new int[]{3,0}));
		*/
		
		//degree 4
		/*
		monomials.add(new Monomial(2, new int[]{0,0}));
		monomials.add(new Monomial(2, new int[]{0,1}));
		monomials.add(new Monomial(2, new int[]{0,2}));
		monomials.add(new Monomial(2, new int[]{0,3}));
		monomials.add(new Monomial(2, new int[]{0,4}));
		monomials.add(new Monomial(2, new int[]{1,0}));
		monomials.add(new Monomial(2, new int[]{1,1}));
		monomials.add(new Monomial(2, new int[]{1,2}));
		monomials.add(new Monomial(2, new int[]{1,3}));
		monomials.add(new Monomial(2, new int[]{2,0}));
		monomials.add(new Monomial(2, new int[]{2,1}));
		monomials.add(new Monomial(2, new int[]{2,2}));
		monomials.add(new Monomial(2, new int[]{3,0}));
		monomials.add(new Monomial(2, new int[]{3,1}));
		monomials.add(new Monomial(2, new int[]{4,0}));
		*/
		
		/*
		monomials.add(new Monomial(3, new int[]{0}));
		monomials.add(new Monomial(8, new int[]{1}));
		monomials.add(new Monomial(65, new int[]{2}));
		*/
		
		/*
		monomials.add(new Monomial(3, new int[]{4,1}));
		monomials.add(new Monomial(5, new int[]{2,3}));
		monomials.add(new Monomial(7, new int[]{3,0}));
		monomials.add(new Monomial(11, new int[]{2,1}));
		monomials.add(new Monomial(13, new int[]{0,3}));
		*/
		
		
		monomials.add(new Monomial(2, new int[]{6,4}));
		monomials.add(new Monomial(3, new int[]{4,1}));
		monomials.add(new Monomial(5, new int[]{2,3}));
		monomials.add(new Monomial(7, new int[]{3,0}));
		monomials.add(new Monomial(11, new int[]{2,1}));
		monomials.add(new Monomial(13, new int[]{0,3}));
		
		
		Polynomial poly=new Polynomial(monomials.toArray(new Monomial[0]));
		long time=System.nanoTime();
		Object[] eval=poly.findBestEval(new ArrayList<Monomial>(), poly.monomials);
		time=System.nanoTime()-time;
		System.out.println("Time: "+time);
		HornersMethodPolynomial hmp=(HornersMethodPolynomial)eval[0];
		System.out.println(hmp+" Operations: "+((int)eval[2]+(int)eval[3]));
		System.out.print("Monomials to multiply to: ");
		for(Monomial monomial: (Monomial[])eval[1])
		{
			System.out.print(monomial.toString()+" ");
		}
	}

}
