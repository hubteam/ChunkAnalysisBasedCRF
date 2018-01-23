package com.wxw.feature;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.asher_stern.crf.crf.filters.CrfFilteredFeature;
import com.asher_stern.crf.crf.filters.TokenAndTagFilter;
import com.asher_stern.crf.crf.filters.TwoTagsFilter;
import com.asher_stern.crf.crf.run.CrfFeatureGenerator;
import com.asher_stern.crf.utilities.CrfException;
import com.asher_stern.crf.utilities.TaggedToken;

/**
 * 为chunk生成标注的CRF特征
 * @author 王馨苇
 *
 */
public class StandardFeatureGenerator extends CrfFeatureGenerator<String,String>{

	protected Set<CrfFilteredFeature<String, String>> setFilteredFeatures = null;
	
	public StandardFeatureGenerator(Iterable<? extends List<? extends TaggedToken<String,String>>> corpus,
			Set<String> tags) {
		super(corpus, tags);
	}

	@Override
	public void generateFeatures() {
		setFilteredFeatures = new LinkedHashSet<CrfFilteredFeature<String,String>>();
		addTokenAndTagFeatures();
		addTagTransitionFeatures();		
	}

	private void addTokenAndTagFeatures(){
		for (List<? extends TaggedToken<String, String> > sentence : corpus){
			for (int i = 0; i < sentence.size(); i++) {
				String w_2, w_1, w0, w1, w2, p_2, p_1, p0, p1, p2, c_1, pf0, af0;
				w_2 = w_1 = w0 = w1 = w2 = p_2 = p_1 = p0 = p1 = p2 = c_1 = pf0 = af0 = null;
				w0 = sentence.get(i).getToken().split("_")[0];
				p0 = sentence.get(i).getToken().split("_")[1];
				if(sentence.get(i).getToken().split("_")[0].length() >1){
					pf0 = w0.substring(0, 2);
					af0 = w0.substring(w0.length()-2,w0.length());
				}else{
					pf0 = af0 = w0;
				}
				if(sentence.size() > i+1){
					w1 = sentence.get(i+1).getToken().split("_")[0];
					p1 = sentence.get(i+1).getToken().split("_")[1];
					if(sentence.size() > i+2){
						w2 = sentence.get(i+2).getToken().split("_")[0];
						p2 = sentence.get(i+2).getToken().split("_")[1];
					}
				}
				if(i-1 >= 0){
					w_1 = sentence.get(i-1).getToken().split("_")[0];
					p_1 = sentence.get(i-1).getToken().split("_")[1];
					c_1 = sentence.get(i-1).getTag();
					if(i-2 >= 0){
						w_2 = sentence.get(i-2).getToken().split("_")[0];
						p_2 = sentence.get(i-2).getToken().split("_")[1];
					}
				}
				if(w_2 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w_2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w_2, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_2, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w_1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w_1, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_1, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(c_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(c_1, sentence.get(i).getTag()),
									true
									)
							);
				}
				setFilteredFeatures.add(
						new CrfFilteredFeature<String, String>(
								new TokenAndTagFeature(w0, sentence.get(i).getTag()),
								new TokenAndTagFilter<String, String>(w0, sentence.get(i).getTag()),
								true
								)
						);
				setFilteredFeatures.add(
						new CrfFilteredFeature<String, String>(
								new TokenAndTagFeature(af0, sentence.get(i).getTag()),
								new TokenAndTagFilter<String, String>(af0, sentence.get(i).getTag()),
								true
								)
						);
				setFilteredFeatures.add(
						new CrfFilteredFeature<String, String>(
								new TokenAndTagFeature(pf0, sentence.get(i).getTag()),
								new TokenAndTagFilter<String, String>(pf0, sentence.get(i).getTag()),
								true
								)
						);
				setFilteredFeatures.add(
						new CrfFilteredFeature<String, String>(
								new TokenAndTagFeature(p0, sentence.get(i).getTag()),
								new TokenAndTagFilter<String, String>(p0, sentence.get(i).getTag()),
								true
								)
						);
				if(w1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w1, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w2 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w2, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p2, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w_1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w_1+w0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w_1+w0, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_1+p0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_1+p0, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w0+w1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w0+w1, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p0+p1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p0+p1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w_1 != null && w1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w_1+w1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w_1+w1, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_1+p0+p1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_1+p0+p1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p_2 != null && p_1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_2+p_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_2+p_1, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_2+p_1+p0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_2+p_1+p0, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p1 != null && p2 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p1+p2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p1+p2, sentence.get(i).getTag()),
									true
									)
							);
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p0+p1+p2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p0+p1+p2, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w_1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w_1+p0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w_1+p0, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w0+p1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w0+p1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p2 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w0+p2, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w0+p2, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w1+p0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w1+p0, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(c_1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p0+c_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p0+c_1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w_2 != null && p_1 != null ){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w_2+p_1+p0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w_2+p_1+p0, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p_1 != null ){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w0+p_1+p0, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w0+p_1+p0, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p1 != null ){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w1+p0+p1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w1+p0+p1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p_1 != null ){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p_1+p0+c_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p_1+p0+c_1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(p1 != null && c_1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(p0+p1+c_1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(p0+p1+c_1, sentence.get(i).getTag()),
									true
									)
							);
				}
				if(w1 != null){
					setFilteredFeatures.add(
							new CrfFilteredFeature<String, String>(
									new TokenAndTagFeature(w0+w1+p1, sentence.get(i).getTag()),
									new TokenAndTagFilter<String, String>(w0+w1+p1, sentence.get(i).getTag()),
									true
									)
							);
				}
			}
		}
	}
	
	private void addTagTransitionFeatures(){
		for (String tag : tags){
			setFilteredFeatures.add(
					new CrfFilteredFeature<String,String>(
							new TagTransitionFeature(null, tag),
							new TwoTagsFilter<String, String>(tag, null),
							true)
					);
			
			for (String previousTag : tags){
				setFilteredFeatures.add(
						new CrfFilteredFeature<String,String>(
								new TagTransitionFeature(previousTag, tag),
								new TwoTagsFilter<String, String>(tag, previousTag),
								true)
						);
			}
		}
	}
	
	@Override
	public Set<CrfFilteredFeature<String, String>> getFeatures() {
		if (null==setFilteredFeatures) {
			throw new CrfException("Features were not generated.");
	    }
		return setFilteredFeatures;
	}
}
