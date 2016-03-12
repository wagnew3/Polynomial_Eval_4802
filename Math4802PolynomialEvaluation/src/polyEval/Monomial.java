package polyEval;

import java.util.Arrays;

public class Monomial 
{
	
	public double coefficient;
	public int[] exponents;
	
	public Monomial(double coefficient, int[] exponents)
	{
		this.coefficient=coefficient;
		this.exponents=exponents;
	}
	
	@Override
	public int hashCode() 
	{
		int hashCode=0;
		for(int ind=0; ind<exponents.length; ind++)
		{
			hashCode+=(exponents[ind]%256-128)<<(8*ind);
		}
		return hashCode;
	}

	@Override
	public boolean equals(Object other) 
	{
		if(coefficient!=((Monomial)other).coefficient
				|| !Arrays.equals(exponents, ((Monomial)other).exponents))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public Monomial clone()
	{
		return new Monomial(coefficient, exponents.clone());
	}
	
	public void divide(Monomial divisor)
	{
		coefficient/=divisor.coefficient;
		for(int varInd=0; varInd<exponents.length; varInd++)
		{
			exponents[varInd]-=divisor.exponents[varInd];
		}
	}
	
	public void multiply(Monomial mult)
	{
		coefficient*=mult.coefficient;
		for(int varInd=0; varInd<exponents.length; varInd++)
		{
			exponents[varInd]+=mult.exponents[varInd];
		}
	}
	
	public String toString()
	{
		String monomial="";
		if(coefficient!=1.0)
		{
			monomial+=""+coefficient;
		}
		for(int varInd=0; varInd<exponents.length; varInd++)
		{
			if(exponents[varInd]!=0)
			{
				monomial+="x"+varInd+"^("+exponents[varInd]+")";
			}
		}
		return monomial;
	}
	
	public boolean isOne()
	{
		for(int varInd=0; varInd<exponents.length; varInd++)
		{
			if(exponents[varInd]!=0)
			{
				return false;
			}
		}
		if(coefficient==1.0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean isCoEff()
	{
		for(int varInd=0; varInd<exponents.length; varInd++)
		{
			if(exponents[varInd]!=0)
			{
				return false;
			}
		}
		return true;
	}

}
