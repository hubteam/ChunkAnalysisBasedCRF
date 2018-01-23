package com.wxw.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.asher_stern.crf.utilities.TaggedToken;

/**
 * ��ȡ����
 * @author ��ܰέ
 *
 */
public class ChunkCorpus implements Iterable<List<TaggedToken<String, String>>>{

	private List<String> chunkTags = new ArrayList<>();
	private List<String> words = new ArrayList<>();
	private List<String> poses = new ArrayList<>();
	private List<List<TaggedToken<String, String>>> sampleList;
	
	public ChunkCorpus(File file) throws IOException{
		super();
		sampleList = read(file);
	}
	
	@Override
	public Iterator<List<TaggedToken<String, String>>> iterator() {
		return sampleList.iterator();
	}
	
	private List<List<TaggedToken<String, String>>> read(File file) throws IOException {
		List<List<TaggedToken<String, String>>> samples = new ArrayList<>();
		InputStreamReader ireader = new InputStreamReader(new FileInputStream(file), "utf8");
		BufferedReader reader = new BufferedReader(ireader);
		
		String line = null;
		while((line = reader.readLine()) != null) {
			line = line.trim();
			if(!line.equals("")) {
				List<TaggedToken<String, String>> sample = new ArrayList<>();
				chunkTags.clear();
				words.clear();
				poses.clear();
				parse(line);
				for(int i = 0; i < chunkTags.size(); i++) {
					TaggedToken<String, String> taggedToken = new TaggedToken<String, String>(words.get(i)+"_"+poses.get(i),chunkTags.get(i));
					sample.add(taggedToken);
				}
				samples.add(sample);
			}
		}
		reader.close();
		
		return samples;
	}
	
	private void parse(String sentence){

		boolean isInChunk = false;							//��ǰ���Ƿ��������
		List<String> wordTagsInChunk = new ArrayList<>();	//��ʱ�洢������еĴ������
		String[] wordTag = null;							//������Ա�ע
		String chunk = null;								//���ı�ǩ
		String[] content = sentence.split("\\s+");
		for(String string : content) {
			if(isInChunk) {	//��ǰ���������
				if(string.contains("]")) {//��ǰ�������Ľ���
					String[] strings = string.split("]");
					wordTagsInChunk.add(strings[0]);
					chunk = strings[1];
					isInChunk = false;
				}else {
					wordTagsInChunk.add(string);
				}
			}else {//��ǰ�ʲ��������
				if(wordTagsInChunk != null && chunk != null) {//��һ������еĴ�δ�����ȴ�����һ������еĴ�	
					wordTag = wordTagsInChunk.get(0).split("/");
					words.add(wordTag[0]);
					poses.add(wordTag[1]);
					chunkTags.add(chunk + "_B");
					
					if(wordTagsInChunk.size() > 2) {
						for(int i = 1; i < wordTagsInChunk.size() - 1; i++) {
							wordTag = wordTagsInChunk.get(i).split("/");
							words.add(wordTag[0]);
							poses.add(wordTag[1]);
							chunkTags.add(chunk + "_I");
						}
					}
					wordTag = wordTagsInChunk.get(wordTagsInChunk.size() - 1).split("/");
					words.add(wordTag[0]);
					poses.add(wordTag[1]);
					chunkTags.add(chunk + "_E");
					
					wordTagsInChunk = new ArrayList<>();
					chunk = null;
					
					if(string.startsWith("[")) {
						wordTagsInChunk.add(string.replace("[", ""));
						isInChunk = true;
					}else {
						wordTag = string.split("/");
						words.add(wordTag[0]);
						poses.add(wordTag[1]);
						chunkTags.add("_O");
					}
					
				}else {
					if(string.startsWith("[")) {
						wordTagsInChunk.add(string.replace("[", ""));
						isInChunk = true;
					}else {
						wordTag = string.split("/");
						words.add(wordTag[0]);
						poses.add(wordTag[1]);
						chunkTags.add("_O");
					}
				}
			}
		}
		
		//���ӽ�β����飬���н���
		if(wordTagsInChunk != null && chunk != null) {
			wordTag = wordTagsInChunk.get(0).split("/");
			words.add(wordTag[0]);
			poses.add(wordTag[1]);
			chunkTags.add(chunk + "_B");
			
			if(wordTagsInChunk.size() > 2) {
				for(int i = 1; i < wordTagsInChunk.size() - 1; i++) {
					wordTag = wordTagsInChunk.get(i).split("/");
					words.add(wordTag[0]);
					poses.add(wordTag[1]);
					chunkTags.add(chunk + "_I");
				}
			}
			wordTag = wordTagsInChunk.get(wordTagsInChunk.size() - 1).split("/");
			words.add(wordTag[0]);
			poses.add(wordTag[1]);
			chunkTags.add(chunk + "_E");
		}
	}
}
