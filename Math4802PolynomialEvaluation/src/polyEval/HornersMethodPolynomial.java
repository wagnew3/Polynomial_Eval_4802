package polyEval;

import java.util.Arrays;
import java.util.List;

public class HornersMethodPolynomial 
{
	
	public List<Monomial> monomials;
	
	public HornersMethodPolynomial(Monomial[] monomials)
	{
		this.monomials=Arrays.asList(monomials);
	}
	
	@Override
	public String toString()
	{
		String 	polynomial=monomials.get(0).toString();
		for(int monomialInd=1; monomialInd<monomials.size(); monomialInd++)
		{
			if(monomialInd%2==0)
			{
				polynomial+=" + "+monomials.get(monomialInd).toString();
			}
			else if(!monomials.get(monomialInd).isOne())
			{
				polynomial="("+polynomial+")*"+monomials.get(monomialInd).toString();
			}
		}
		return polynomial;
	}
	
	//[0]=adds, [1]=multiplies
	public int[] computationComplexity()
	{
		int adds=0; 
		int multiplies=0;
		for(int monomialInd=0; monomialInd<monomials.size(); monomialInd++)
		{
			if(monomialInd%2==0)
			{
				if(monomials.get(monomialInd).coefficient!=0.0)
				{
					if(monomials.get(monomialInd).coefficient!=1.0
							&& !monomials.get(monomialInd).isCoEff())
					{
						multiplies++;
					}
					if(monomialInd>0)
					{
						adds++;
					}
				}
			}
			else if(!monomials.get(monomialInd).isOne())
			{
				multiplies++;
			}
		}
		return new int[]{adds, multiplies};
	}

}
