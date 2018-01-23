package com.wxw.sample;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.asher_stern.crf.utilities.TaggedToken;

public class LimitedSizeChunkTagCorpusReader<K,G> implements Iterator<List<TaggedToken<K, G>>>
{
	public LimitedSizeChunkTagCorpusReader(Iterator<List<TaggedToken<K, G>>> realCorpus, int size)
	{
		super();
		this.realCorpus = realCorpus;
		this.size = size;
	}

	@Override
	public boolean hasNext()
	{
		return (realCorpus.hasNext()&&(index<size));
	}

	@Override
	public List<TaggedToken<K, G> > next()
	{
		if (index<size)
		{
			++index;
			return realCorpus.next();
		}
		else throw new NoSuchElementException();
	}

	
	private final Iterator<List<TaggedToken<K, G>>> realCorpus;
	private final int size;
	
	private int index=0;
}
