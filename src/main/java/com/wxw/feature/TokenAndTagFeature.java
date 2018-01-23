package com.wxw.feature;

import com.asher_stern.crf.crf.CrfFeature;
import static com.asher_stern.crf.utilities.MiscellaneousUtilities.equalObjects;

/**
 * 当前位置状态特征
 * @author 王馨苇
 *
 */
public class TokenAndTagFeature extends CrfFeature<String, String>{

	private final String forToken;
	private final String forTag;
	private static final long serialVersionUID = -3196603759120595624L;
	
	public TokenAndTagFeature(String forToken, String forTag)
	{
		super();
		this.forToken = forToken;
		this.forTag = forTag;
	}
	
	@Override
	public double value(String[] sequence, int indexInSequence,
			String currentTag, String previousTag) {
		double ret = 0.0;
		if (equalObjects(sequence[indexInSequence],forToken) && equalObjects(currentTag,forTag))
		{
			ret = 1.0;
		}
		return ret;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forTag == null) ? 0 : forTag.hashCode());
		result = prime * result + ((forToken == null) ? 0 : forToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenAndTagFeature other = (TokenAndTagFeature) obj;
		if (forTag == null) {
			if (other.forTag != null)
				return false;
		} else if (!forTag.equals(other.forTag))
			return false;
		if (forToken == null) {
			if (other.forToken != null)
				return false;
		} else if (!forToken.equals(other.forToken))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "TokenAndTagFeature [forToken=" + forToken + ", forTag="
				+ forTag + "]";
	}
}
