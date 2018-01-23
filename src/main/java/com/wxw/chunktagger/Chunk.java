package com.wxw.chunktagger;

import java.util.Arrays;
import java.util.List;

/**
 * �����Ϣ��
 * @author ��ܰέ
 *
 */
public class Chunk {

	private String type;
	
	private String[] tokens;
	
	private String string;
	
	private int start;
	
	private int end;
	
	public Chunk() {
		
	}
	
	public Chunk(String type, String token, int start, int end) {
		this(type, new String[]{token}, start, end);
	}
	
	public Chunk(String type, List<String> tokens, int start, int end) {
		this(type, tokens.toArray(new String[tokens.size()]), start, end);
	}
	
	public Chunk(String type, String[] tokens, int start, int end) {
		this.type = type;
		this.tokens = tokens;
		this.start = start;
		this.end = end;
		
		string = "";
		for(String token : tokens)
			string += token + "  ";
		string = string.trim();
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setWords(String[] tokens) {
		this.tokens = tokens;
	}
	
	public void setString(String string) {
		this.string = string;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * �����������
	 * @return	�������
	 */
	public String getType() {
		return type;
	}

	/**
	 * ��ȡ����еĴ���
	 * @return
	 */
	public String[] getTokens() {
		return tokens;
	}

	/**
	 * ��������ַ���
	 * @return	����ַ���
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * ���������ʼλ��
	 * @return	�����ʼλ��
	 */
	public int getStart() {
		return start;
	}
	
	/**
	 * ����������λ��
	 * @return	������λ��
	 */
	public int getEnd() {
		return end;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		result = prime * result + Arrays.hashCode(tokens);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Chunk other = (Chunk) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string))
			return false;
		if (!Arrays.equals(tokens, other.tokens))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(type.equals("O"))
			return string;
		
		return "["+ string + "]" + type;
	}
}
