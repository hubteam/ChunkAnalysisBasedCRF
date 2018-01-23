package com.wxw.feature;

import java.util.LinkedHashSet;
import java.util.Set;

import com.asher_stern.crf.crf.filters.Filter;
import com.asher_stern.crf.crf.filters.FilterFactory;
import com.asher_stern.crf.crf.filters.TokenAndTagFilter;
import com.asher_stern.crf.crf.filters.TwoTagsFilter;

public class StandardFilterFactory implements FilterFactory<String, String>{

	private static final long serialVersionUID = 6283122214266870374L;

	@Override
	public Set<Filter<String, String>> createFilters(String[] sequence, int tokenIndex, String currentTag,
			String previousTag) {
		String w_2, w_1, w0, w1, w2, p_2, p_1, p0, p1, p2, c_1, pf0, af0;
		w_2 = w_1 = w0 = w1 = w2 = p_2 = p_1 = p0 = p1 = p2 = c_1 = pf0 = af0 = null;
		c_1 = previousTag;
		w0 = sequence[tokenIndex].split("_")[0];
		p0 = sequence[tokenIndex].split("_")[1];
		if(sequence[tokenIndex].split("_")[0].length() >1){
			pf0 = w0.substring(0, 2);
			af0 = w0.substring(w0.length()-2,w0.length());
		}else{
			pf0 = af0 = w0;
		}
		if(sequence.length > tokenIndex+1){
			w1 = sequence[tokenIndex+1].split("_")[0];
			p1 = sequence[tokenIndex+1].split("_")[1];
			if(sequence.length > tokenIndex+2){
				w2 = sequence[tokenIndex+2].split("_")[0];
				p2 = sequence[tokenIndex+2].split("_")[1];
			}
		}
		if(tokenIndex-1 >= 0){
			w_1 = sequence[tokenIndex-1].split("_")[0];
			p_1 = sequence[tokenIndex-1].split("_")[1];
			if(tokenIndex-2 >= 0){
				w_2 = sequence[tokenIndex-2].split("_")[0];
				p_2 = sequence[tokenIndex-2].split("_")[1];
			}
		}
		Set<Filter<String, String>> ret = new LinkedHashSet<Filter<String,String>>();
		ret.add(new TwoTagsFilter<String, String>(currentTag, previousTag));
		if(w_2 != null){
			ret.add(new TokenAndTagFilter<String, String>(w_2, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p_2, currentTag));
		}
		if(w_1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w_1, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p_1, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(c_1, currentTag));
		}
		ret.add(new TokenAndTagFilter<String, String>(w0, currentTag));
		ret.add(new TokenAndTagFilter<String, String>(af0, currentTag));
		ret.add(new TokenAndTagFilter<String, String>(pf0, currentTag));
		ret.add(new TokenAndTagFilter<String, String>(p0, currentTag));
		if(w1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w1, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p1, currentTag));
		}
		if(w2 != null){
			ret.add(new TokenAndTagFilter<String, String>(w2,currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p2, currentTag));
		}
		if(w_1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w_1+w0, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p_1+p0, currentTag));
		}
		if(w1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w0+w1, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p0+p1, currentTag));
		}
		if(w_1 != null && w1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w_1+w1, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p_1+p0+p1, currentTag));
		}
		if(p_2 != null && p_1 != null){
			ret.add(new TokenAndTagFilter<String, String>(p_2+p_1, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p_2+p_1+p0, currentTag));
		}
		if(p1 != null && p2 != null){
			ret.add(new TokenAndTagFilter<String, String>(p1+p2, currentTag));
			ret.add(new TokenAndTagFilter<String, String>(p0+p1+p2, currentTag));
		}
		if(w_1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w_1+p0, currentTag));
		}
		if(p1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w0+p1, currentTag));
		}
		if(p2 != null){
			ret.add(new TokenAndTagFilter<String, String>(w0+p2, currentTag));
		}
		if(w1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w1+p0, currentTag));
		}
		if(c_1 != null){
			ret.add(new TokenAndTagFilter<String, String>(p0+c_1, currentTag));
		}
		if(w_2 != null && p_1 != null ){
			ret.add(new TokenAndTagFilter<String, String>(w_2+p_1+p0, currentTag));
		}
		if(p_1 != null ){
			ret.add(new TokenAndTagFilter<String, String>(w0+p_1+p0, currentTag));
		}
		if(p1 != null ){
			ret.add(new TokenAndTagFilter<String, String>(w1+p0+p1, currentTag));
		}
		if(p_1 != null ){
			ret.add(new TokenAndTagFilter<String, String>(p_1+p0+c_1, currentTag));
		}
		if(p1 != null && c_1 != null){
			ret.add(new TokenAndTagFilter<String, String>(p0+p1+c_1, currentTag));
		}
		if(w1 != null){
			ret.add(new TokenAndTagFilter<String, String>(w0+w1+p1, currentTag));
		}
		return null;
	}
}
