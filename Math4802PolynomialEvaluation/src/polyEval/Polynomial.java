package polyEval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Polynomial 
{
	
	protected ArrayList<Monomial> monomials;
	protected HashMap<MonomialList, MonomialList>[] coeffListNMults;
	
	public Polynomial(Monomial[] monomials)
	{
		this.monomials=new ArrayList<>();
		this.monomials.addAll(Arrays.asList(monomials));
		coeffListNMults=new HashMap[100];
	}
	
	//[0]=HornersMethodPolynomial, [1]=var multiplications, [2]=number adds, [3]=number mults
	public Object[] findBestEval(ArrayList<Monomial> assigned, ArrayList<Monomial> unassigned)
	{
		Object[] bestEval=null;
		int lowestNumberOfOps=Integer.MAX_VALUE;
		
		int highestInd=-1;
		for(int unassignedInd=0; unassignedInd<unassigned.size(); unassignedInd++)
		{
			Monomial unassignedTerm=unassigned.get(unassignedInd);
			boolean gteAllTerms=true;
			for(int unassigned2Ind=0; unassigned2Ind<unassigned.size(); unassigned2Ind++)
			{
				for(int expInd=0; expInd<unassignedTerm.exponents.length; expInd++)
				{
					if(unassignedTerm.exponents[expInd]<unassigned.get(unassigned2Ind).exponents[expInd])
					{
						gteAllTerms=false;
						break;
					}
				}
				if(!gteAllTerms)
				{
					break;
				}
			}
			if(gteAllTerms)
			{
				highestInd=unassignedInd;
			}
		}
		
		for(int unassignedInd=0; unassignedInd<unassigned.size(); unassignedInd++)
		{
			if(highestInd==-1 || highestInd==unassignedInd)
			{
				Monomial unassignedTerm=unassigned.get(unassignedInd);
				boolean ltSomeTerm=false;
				for(Monomial assignedTerm: assigned)
				{
					for(int expInd=0; expInd<unassignedTerm.exponents.length; expInd++)
					{
						if(unassignedTerm.exponents[expInd]<assignedTerm.exponents[expInd])
						{
							ltSomeTerm=true;
							break;
						}
					}
					if(ltSomeTerm)
					{
						break;
					}
				}
				
				if(ltSomeTerm || assigned.isEmpty())
				{
					assigned.add(unassigned.remove(unassignedInd));
					if(!unassigned.isEmpty())
					{
						Object[] eval=findBestEval(assigned, unassigned);
						if(eval!= null && (int)eval[2]+(int)eval[3]<lowestNumberOfOps)
						{
							lowestNumberOfOps=(int)eval[2]+(int)eval[3];
							bestEval=eval;
						}
					}
					else
					{
						Object[] eval=new Object[4];
						eval[0]=hornersMethodPolynomial(assigned);
						int[] hmpAM=((HornersMethodPolynomial)eval[0]).computationComplexity();
						eval[2]=hmpAM[0];
						eval[3]=hmpAM[1];
						
						eval[1]=monomialsToMultiply(((HornersMethodPolynomial)eval[0]).monomials);
						eval[3]=((int)eval[3])+((Monomial[])eval[1]).length-((HornersMethodPolynomial)eval[0]).monomials.get(0).exponents.length;
						
						if((int)eval[2]+(int)eval[3]<lowestNumberOfOps)
						{
							lowestNumberOfOps=(int)eval[2]+(int)eval[3];
							bestEval=eval;
						}
						
					}
					unassigned.add(unassignedInd, assigned.remove(assigned.size()-1));
				}
			}
		}
		return bestEval;
	}
	
	public HornersMethodPolynomial hornersMethodPolynomial(List<Monomial> monomials)
	{
		List<Monomial> cMonomials=cloneMonomial(monomials);
		List<Monomial> hornersMethodPolynomial=new ArrayList<>();
		
		for(int polyInd=cMonomials.size()-1; polyInd>=0; polyInd--)
		{
			Monomial gcd=gcd(cMonomials.subList(0, polyInd+1));
			
			for(int monomialInd=0; monomialInd<=polyInd; monomialInd++)
			{
				cMonomials.get(monomialInd).divide(gcd);
			}
			
			hornersMethodPolynomial.add(0, gcd);
			hornersMethodPolynomial.add(0, cMonomials.get(polyInd));
		}
		return new HornersMethodPolynomial(hornersMethodPolynomial.toArray(new Monomial[0]));
	}
	
	private List<Monomial> cloneMonomial(List<Monomial> monomials)
	{
		List<Monomial> cMonomials=new ArrayList<>();
		for(Monomial monomial: monomials)
		{
			cMonomials.add(monomial.clone());
		}
		return cMonomials;
	}
	
	public Monomial gcd(List<Monomial> monomials)
	{
		int[] leastExponents=new int[monomials.get(0).exponents.length];
		for(int varInd=0; varInd<leastExponents.length; varInd++)
		{
			leastExponents[varInd]=Integer.MAX_VALUE;
		}
		
		for(int monomialInd=0; monomialInd<monomials.size(); monomialInd++)
		{
			for(int varInd=0; varInd<leastExponents.length; varInd++)
			{
				if(leastExponents[varInd]>monomials.get(monomialInd).exponents[varInd])
				{
					leastExponents[varInd]=monomials.get(monomialInd).exponents[varInd];
				}
			}
		}
		return new Monomial(1.0, leastExponents);
	}
	
	public Monomial[] monomialsToMultiply(List<Monomial> targetMonomials)
	{
		HashMap<Monomial, Monomial> targetMonomialsNoCoeffs=new HashMap<>();
		for(Monomial monomial: targetMonomials)
		{
			Monomial noCoeffs=new Monomial(1.0, monomial.exponents);
			if(!noCoeffs.isOne())
			{
				targetMonomialsNoCoeffs.put(noCoeffs, noCoeffs);
			}
		}
		
		MonomialList targetList=new MonomialList(targetMonomialsNoCoeffs);
		HashMap<Monomial, Monomial> baseMonomials=new HashMap<>();
		for(int varInd=0; varInd<targetMonomials.get(0).exponents.length; varInd++)
		{
			int[] exponents=new int[targetMonomials.get(0).exponents.length];
			exponents[varInd]=1;
			Monomial baseMonomial=new Monomial(1.0, exponents);
			baseMonomials.put(baseMonomial, baseMonomial);
		}
		MonomialList baseMonomialList=new MonomialList(baseMonomials);
		HashMap<MonomialList, MonomialList> currentMonomialLists=new HashMap<>();
		currentMonomialLists.put(baseMonomialList, baseMonomialList);
		MonomialList resultMonomialList=null;
		
		int n=targetMonomialsNoCoeffs.size()-baseMonomialList.monomials.size();
		if(coeffListNMults[n]==null)
		{
			n=1;
		}
		
		while((resultMonomialList=monomialListListContains(currentMonomialLists, targetList))==null)
		{
			if(coeffListNMults[n]!=null)
			{
				currentMonomialLists=coeffListNMults[n];
			}
			else
			{
				HashMap<MonomialList, MonomialList> newMonomialLists=new HashMap<>();
				HashMap<Monomial, Monomial> triedTerms=new HashMap<>();
				
				for(MonomialList termList: currentMonomialLists.keySet())
				{
					for(Monomial termListTerm: termList.monomials.keySet())
					{
						for(Monomial multListTerm: termList.monomials.keySet())
						{
							MonomialList newMonomialList=termList.multiplyTerm(multListTerm, termListTerm);
							Monomial mult=multListTerm.clone();
							mult.multiply(termListTerm);
							if(!expsGreaterThanAllTargetExps(newMonomialList.monomials.get(mult), targetMonomialsNoCoeffs))
							{
								newMonomialLists.put(newMonomialList, newMonomialList);
							}
						}
					}
				}
				currentMonomialLists=newMonomialLists;
				coeffListNMults[n]=newMonomialLists;
			}
			n++;
		}
		return resultMonomialList.monomials.keySet().toArray(new Monomial[0]);
	}
	
	//null: no list contains
	private MonomialList monomialListListContains(HashMap<MonomialList, MonomialList> monomialListList, MonomialList targetList)
	{
		for(MonomialList monomialList: monomialListList.keySet())
		{
			if(monomialList.contains(targetList))
			{
				return monomialList;
			}
		}
		return null;
	}
	
	private boolean expsGreaterThanAllTargetExps(Monomial monomial, HashMap<Monomial, Monomial> targetMonomials)
	{
		for(int expInd=0; expInd<monomial.exponents.length; expInd++)
		{
			boolean oneGeq=false;
			for(Monomial targetMonomial: targetMonomials.keySet())
			{
				if(targetMonomial.exponents[expInd]>=monomial.exponents[expInd])
				{
					oneGeq=true;
					break;
				}
			}
			if(!oneGeq)
			{
				return true;
			}
		}
		return false;
	}

}

class MonomialList
{
	
	public HashMap<Monomial, Monomial> monomials;
	
	public MonomialList(HashMap<Monomial, Monomial> monomials)
	{
		this.monomials=monomials;
	}
	
	public boolean contains(MonomialList target)
	{
		for(Monomial targetMonomial: target.monomials.keySet())
		{
			if(monomials.get(targetMonomial)==null)
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return contains((MonomialList)other) && ((MonomialList)other).contains(this);
	}
	
	@Override
	public int hashCode() 
	{
		int hashCode=0;
		for(Monomial entry: monomials.keySet())
		{
			hashCode+=hashL(entry.hashCode(), 29);
		}
		return hashCode;
	}
	
	public MonomialList multiplyTerm(Monomial term, Monomial mult)
	{
		HashMap<Monomial, Monomial> terms=cloneMonomial(monomials);
		Monomial clonedTerm=terms.get(term).clone();
		clonedTerm.multiply(mult);
		terms.put(clonedTerm, clonedTerm);
		return new MonomialList(terms);
	}
	
	private HashMap<Monomial, Monomial> cloneMonomial(HashMap<Monomial, Monomial> monomials)
	{
		HashMap<Monomial, Monomial> cMonomials=new HashMap<>();
		for(Monomial monomial: monomials.keySet())
		{
			Monomial cMonomial=monomial;
			cMonomials.put(cMonomial, cMonomial);
		}
		return cMonomials;
	}
	
	public static int hashL(long data, int seed) 
	  {

	    int c1 = 0xcc9e2d51;
	    int c2 = 0x1b873593;
	    int len=8;
	    int h1 = seed;

	    //for (int i = 0; i < roundedEnd; i += 4) {

	      long k1 = ((data) & 0xff) | ((data & 0xff) << 8) | ((data & 0xff) << 16) | (data << 24);
	      k1 *= c1;
	      k1 = (k1 << 15) | (k1 >>> 17);  
	      k1 *= c2;
	      
	      h1 ^= k1;
	      h1 = (h1 << 13) | (h1 >>> 19);  
	      h1 = h1 * 5 + 0xe6546b64;
	      data/=100;
	      k1 = (data & 0xff) | ((data & 0xff) << 8) | ((data & 0xff) << 16) | (data << 24);
	      k1 *= c1;
	      k1 = (k1 << 15) | (k1 >>> 17);  
	      k1 *= c2;
	     
	      h1 ^= k1;
	      h1 = (h1 << 13) | (h1 >>> 19);  
	      h1 = h1 * 5 + 0xe6546b64;

	      k1 = (data/100 & 0xff);
	      k1 *= c1;
	      k1 = (k1 << 15) | (k1 >>> 17);  
	      k1 *= c2;
	 
	      h1 ^= k1;
	      h1 = (h1 << 13) | (h1 >>> 19);  
	      h1 = h1 * 5 + 0xe6546b64;
	        

	      k1 = 0;

	    h1 ^= len;


	    h1 ^= h1 >>> 16;
	    h1 *= 0x85ebca6b;
	    h1 ^= h1 >>> 13;
	    h1 *= 0xc2b2ae35;
	    h1 ^= h1 >>> 16;

	    return h1;
	  }
	
}
