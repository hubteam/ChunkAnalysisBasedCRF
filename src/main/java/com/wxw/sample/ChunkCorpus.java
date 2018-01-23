package com.wxw.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
		InputStreamReader ireader = new InputStreamReader(new FileInputStream(file), "gbk");
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
		System.out.println(Arrays.asList(content).toString());
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
						chunkTags.add("O");
					}
					
				}else {
					if(string.startsWith("[")) {
						wordTagsInChunk.add(string.replace("[", ""));
						isInChunk = true;
					}else {
						wordTag = string.split("/");
						words.add(wordTag[0]);
						poses.add(wordTag[1]);
						chunkTags.add("O");
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
	
	/**
     * �Ի����ֵĵõ���Ӧ������ʵ���ע����
     * @param tagsandposesPre �ֵı߽�_����ʵ���ע ���ָ�ʽ��ɵ�����
     * @return
     */
	public static String[] toChunkTag(String[] tagsandposesPre) {
		List<String> poses = new ArrayList<String>();
		int i = 0;
		while(i < tagsandposesPre.length){
			if(tagsandposesPre[i].equals("O")){
				poses.add(tagsandposesPre[i]);
				
				while(tagsandposesPre[i].equals("O")){
					i++;
					if(i == tagsandposesPre.length){
						break;
					}
				}				
			}else{
				String tag = tagsandposesPre[i].split("_")[1];
				String pos = tagsandposesPre[i].split("_")[0];
				if(tag.equals("B")){
					poses.add(pos);					
				}else if(tag.equals("I")){
					
				}else if(tag.equals("E")){
					
				}else if(tag.equals("S")){
					poses.add(pos);
				}
				i++;
				if(i == tagsandposesPre.length){
					break;
				}
			}
		}
		return poses.toArray(new String[poses.size()]);
	}
	
	/**
     * �õ���Ӧ�ķִ�����
     * @param characters �����ֵ����ַ����У�����ǻ��ڴʵľ��Ǵ�������
     * @param tagsandposesPre �ֵı߽�_ʵ���� ���ָ�ʽ��ɵ�����
     * @return
     */
	public static String[] toChunkToken(String[] characters, String[] tagsandposesPre) {
		String word = new String();
        ArrayList<String> words = new ArrayList<String>();       
		int i = 0;
		while(i < tagsandposesPre.length){
			if(tagsandposesPre[i].equals("O")){				
				while(tagsandposesPre[i].equals("O")){
					word += characters[i];
					i++;
					if(i == tagsandposesPre.length){
						break;
					}
				}
				words.add(word);
				word = "";
			}else{
				word += characters[i];
				String tag = tagsandposesPre[i].split("_")[1];

				 if (tag.equals("S") || tag.equals("B")) {
		                words.add(word);
		                word = "";
		            }
				i++;
				if(i == tagsandposesPre.length){
					break;
				}
			}
		}
        return words.toArray(new String[words.size()]);
	}
}
