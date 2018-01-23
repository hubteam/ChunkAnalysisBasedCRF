package com.wxw.tool;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.asher_stern.crf.utilities.ExceptionUtil;
import com.asher_stern.crf.utilities.RuntimeUtilities;
import com.asher_stern.crf.utilities.TaggedToken;
import com.asher_stern.crf.utilities.log4j.Log4jInit;
import com.wxw.chunktagger.ChunkTagger;
import com.wxw.crfchunktagger.CrfChunkTaggerTrainer;
import com.wxw.crfchunktagger.CrfChunkTaggerTrainerFactory;
import com.wxw.sample.ChunkCorpus;
import com.wxw.sample.TrainTestChunkTagCorpus;

/**
 * 运行和评估类
 * @author 王馨苇
 *
 */
public class TrainAndEvaluate {

	private final String corpusDirectory;
	private final int trainSize;
	private final int testSize;
	private final String loadSaveDirectoryName;
	
	private String trainingTime = null;
	
	private static final Logger logger = Logger.getLogger(TrainAndEvaluate.class);
	
	public TrainAndEvaluate(String corpusDirectory, int trainSize, int testSize, String loadSaveDirectoryName) {
		this.corpusDirectory = corpusDirectory;
		this.trainSize = trainSize;
		this.testSize = testSize;
		this.loadSaveDirectoryName = loadSaveDirectoryName;
		
		logger.info("trainSize = " + trainSize);
		logger.info("testSize = " + testSize);
	}

	public static void main(String[] args) {
		Log4jInit.init(Level.DEBUG);
		try
		{
			int testSize = 0;
			if (args.length>=3) {testSize = Integer.parseInt(args[2]);}
			String loadSaveDirectoryName = null;
			if (args.length>=4) {loadSaveDirectoryName = args[3];}
			new TrainAndEvaluate(args[0],Integer.parseInt(args[1]),testSize,loadSaveDirectoryName).go();
		}
		catch(Throwable t)
		{
			ExceptionUtil.logException(t, logger);
		}
	}

	private void go() throws IOException {
		TrainTestChunkTagCorpus<String, String> corpus = null;
		try {
			corpus = createCorpus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Training...");
		ChunkTagger posTagger = train(corpus.createTrainCorpus());
		logger.info("Training - done.");
		logger.info(RuntimeUtilities.getUsedMemory());
		
		logger.info("Evaluating...");
//		AccuracyEvaluator evaluator = new AccuracyEvaluator(corpus.createTestCorpus(), posTagger
////				, new PrintWriter(System.out) // comment out this line to prevent tagged test sentence from being printed.
//				);
//		evaluator.evaluate();
//		logger.info(trainingTime);
//		logger.info("Accuracy = " + String.format("%-3.3f", evaluator.getAccuracy()));
//		logger.info("Correct = "+evaluator.getCorrect());
//		logger.info("Incorrect = "+evaluator.getIncorrect());
	}
	
	private TrainTestChunkTagCorpus<String,String> createCorpus() throws IOException
	{
		return new TrainTestChunkTagCorpus<String,String>(trainSize, testSize,
				new ChunkCorpus(new File(corpusDirectory))
				);
	}

	
	private ChunkTagger train(Iterable<? extends List<? extends TaggedToken<String, String>>> corpus)
	{
		long timeInit = new Date().getTime();
		
//		MajorityPosTaggerTrainer trainer = new MajorityPosTaggerTrainer();
//		trainer.train(inMemoryCorpus);
		
		List<List<? extends TaggedToken<String, String>>> corpusAsList = new LinkedList<List<? extends TaggedToken<String, String>>>();
		for (List<? extends TaggedToken<String, String>> sentence : corpus)
		{
			corpusAsList.add(sentence);
		}
		
		CrfChunkTaggerTrainer trainer = new CrfChunkTaggerTrainerFactory().createTrainer(corpusAsList);
		trainer.train(corpusAsList);

		long seconds = (new Date().getTime()-timeInit)/1000;
		trainingTime = "Training time (HH:MM:SS) = "+String.format("%02d:%02d:%02d",(seconds/60)/60,(seconds/60)%60,seconds%60);
		logger.info(trainingTime);
		
		if (loadSaveDirectoryName!=null)
		{
			File saveDirectory = new File(loadSaveDirectoryName);
			logger.info("Saving pos tagger into directory: "+saveDirectory.getAbsolutePath()+" ...");
			trainer.save(saveDirectory);
			logger.info("Save done.");
		}
		
		ChunkTagger chunkTagger = trainer.getTrainedChunkTagger();
		return chunkTagger;
	}
}
