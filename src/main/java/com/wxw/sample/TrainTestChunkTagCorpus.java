package com.wxw.sample;

import java.util.Iterator;
import java.util.List;

import com.asher_stern.crf.postagging.data.LimitedSizePosTagCorpusReader;
import com.asher_stern.crf.utilities.TaggedToken;

public class TrainTestChunkTagCorpus<K,G>
{
	public TrainTestChunkTagCorpus(int trainSize, Iterable<List<TaggedToken<K, G>>> realCorpus)
	{
		this(trainSize,0,realCorpus);
	}

	public TrainTestChunkTagCorpus(int trainSize, int testSize, Iterable<List<TaggedToken<K, G>>> realCorpus)
	{
		super();
		this.trainSize = trainSize;
		this.testSize = testSize;
		this.realCorpus = realCorpus;
	}



	public Iterable<List<TaggedToken<K, G>>> createTrainCorpus()
	{
		if (trainSize>0)
		{
			return new Iterable<List<TaggedToken<K, G>>>()
			{
				@Override
				public Iterator<List<TaggedToken<K, G>>> iterator()
				{
					return new LimitedSizeChunkTagCorpusReader<K,G>(realCorpus.iterator(), trainSize);
				}
			};
		}
		else
		{
			return realCorpus;
		}
	}

	
	public Iterable<List<TaggedToken<K, G>>> createTestCorpus()
	{
		if ( (trainSize<=0) && (testSize<=0) ) return realCorpus;
		
		Iterator<List<TaggedToken<K, G>>> reader = realCorpus.iterator();
		if (trainSize>0)
		{
			for (int i=0;(i<trainSize)&&(reader.hasNext());++i)
			{
				reader.next();
			}
		}
		
		if (testSize>0)
		{
			reader = new LimitedSizePosTagCorpusReader<K,G>(reader, testSize);
		}
		
		final Iterator<List<TaggedToken<K, G>>> finalReader = reader;
		
		return new Iterable<List<TaggedToken<K, G>>>()
		{
			@Override
			public Iterator<List<TaggedToken<K, G>>> iterator()
			{
				return finalReader;
			}
		};
		
	}

	private final int trainSize;
	private final int testSize;
	private final Iterable<List<TaggedToken<K, G>>> realCorpus;
	
}