package fr.bnf.batchTp2.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Loggable {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
