import java.text.MessageFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.print.ServiceUI;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.TaskScheduler;

import sun.security.tools.policytool.LogPerm;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.processengine.enums.ProcessState;
import de.hybris.platform.processengine.model.ProcessTaskLogModel;
import de.hybris.platform.processengine.model.ProcessTaskModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.internal.service.ServicelayerUtils;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.tx.Transaction;
import de.hybris.platform.tx.TransactionBody;
import de.hybris.platform.voucher.model.VoucherModel;

LOGSTRING = "javasystemproxy.groovy";


def doIt() {    
    logProperty("https.proxySet");
    logProperty("https.proxyHost");
    logProperty("https.proxyPort");
    logProperty("https.proxyUser");
    logProperty("https.proxyPassword");    
}

void logProperty(String property) {
    log(property+":"+System.getProperties().get(property));
}


def log(Object s) {
    println(s);
    //Logger.getLogger(LOGSTRING).info(s);
}

doIt();
