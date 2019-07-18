package com.qa.util;

import com.qa.test.InvestorPlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBOperations {

    private static Logger LOG = LoggerFactory.getLogger(DBOperations.class);
    private static String db_prefix = InvestorPlatformUI.testParams.getTestEnvironment() + "_";


    public static String getInvestorIDFromUserEmail(String email) {
        String query = "select i.id investor_id\n" +
                "from " + db_prefix + "user_context.user u\n" +
                "  join " + db_prefix + "customer_context.users_customers cu ON cu.user_id = u.id\n" +
                "  join "+ db_prefix + "investor_context.investor i ON cu.customer_id=i.id\n" +
                "where u.email_address=" + "'" + email + "'";
        return DBConnection.getInvestorIDFromUserEmail(db_prefix + "investor_context", query);
    }

    public static String getBankAccountIDFromUserEmail(String email) {
        LOG.info("Getting bank account ID for user=" + email);
        String query = "select ba.id from user u\n" +
                "  join "+ db_prefix + "customer_context.users_customers uc on u.id=uc.user_id\n" +
                "  join "+ db_prefix + "customer_context.bank_account ba on uc.customer_id=ba.customer_id\n" +
                "where u.email_address=" + "'" + email + "'";
        return DBConnection.getBankAccountIDFromUserEmail(db_prefix + "user_context", query);
    }

}