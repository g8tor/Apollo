environments {
    development {
        // sample config to turn on debug logging in development e.g. for apollo run-local
        log4j.main = {
            debug "grails.app"
        }
        // sample config to edit apollo specific configs in development mode
        apollo {
            gff3.source = "testing"
        }
        dataSource{
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            username = System.getenv("WEBAPOLLO_DB_USERNAME") ?: "apollo"
            password = System.getenv("WEBAPOLLO_DB_PASSWORD") ?: "apollo"
            driverClassName = System.getenv("WEBAPOLLO_DB_DRIVER") ?:"org.postgresql.Driver"
            dialect = System.getenv("WEBAPOLLO_DB_DIALECT") ?: "org.hibernate.dialect.PostgresPlusDialect"
            url = System.getenv("WEBAPOLLO_DB_URI") ?: "jdbc:postgresql://127.0.0.1/apollo"
        }
   }
    production {
        dataSource{
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            username = System.getenv("WEBAPOLLO_DB_USERNAME") ?: "apollo"
            password = System.getenv("WEBAPOLLO_DB_PASSWORD") ?: "apollo"
            driverClassName = System.getenv("WEBAPOLLO_DB_DRIVER") ?:"org.postgresql.Driver"
            dialect = System.getenv("WEBAPOLLO_DB_DIALECT") ?: "org.hibernate.dialect.PostgresPlusDialect"
            url = System.getenv("WEBAPOLLO_DB_URI") ?: "jdbc:postgresql://127.0.0.1/apollo"

            properties {
                // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
                jmxEnabled = true
                initialSize = 5
                maxActive = 50
                minIdle = 5
                maxIdle = 25
                maxWait = 10000
                maxAge = 10 * 60000
                timeBetweenEvictionRunsMillis = 5000
                minEvictableIdleTimeMillis = 60000
                validationQuery = "SELECT 1"
                validationQueryTimeout = 3
                validationInterval = 15000
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                jdbcInterceptors = "ConnectionState"
                defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
            }
        }
   }
}

// Uncomment to make changes
//
// not quite default apollo settings
apollo {
    admin{
        username = System.getenv("APOLLO_ADMIN_EMAIL")?:"apollo@localhost.com"
        password = System.getenv("APOLLO_ADMIN_PASSWORD")?:"apollo"
        firstName = System.getenv("APOLLO_ADMIN_FIRST_NAME")?: "Apollo"
        lastName = System.getenv("APOLLO_ADMIN_LAST_NAME")?: "User"
    }
  default_minimum_intron_size = 1
  history_size = 0
  overlapper_class = "org.bbop.apollo.sequence.OrfOverlapper"
  track_name_comparator = "/config/track_name_comparator.js"
  use_cds_for_new_transcripts = true
  user_pure_memory_store = true
  translation_table = "/config/translation_tables/ncbi_1_translation_table.txt"
  is_partial_translation_allowed = false // unused so far
  get_translation_code = 1
  only_owners_delete = false
  google_analytics = ["UA-49907870-1","UA-62921593-1", "UA-27627304-1"]
  sequence_search_tools = [
    blat_nuc: [
      search_exe: "/usr/local/bin/blat",
      search_class: "org.bbop.apollo.sequence.search.blat.BlatCommandLineNucleotideToNucleotide",
      name: "Blat nucleotide",
      params: ""
    ],
    blat_prot: [
      search_exe: "/usr/local/bin/blat",
      search_class: "org.bbop.apollo.sequence.search.blat.BlatCommandLineProteinToNucleotide",
      name: "Blat protein",
      params: ""
    ]
  ]



  splice_donor_sites = [ "GT" ]
  splice_acceptor_sites = [ "AG"]
  gff3.source= "Apollo"
  bootstrap = false

  info_editor = {
    feature_types = "default"
    attributes = true
    dbxrefs = true
    pubmed_ids = true
    go_ids = true
    comments = true
  }
}

jbrowse {
   git {
       url= "https://github.com/GMOD/jbrowse"
       tag = "1.16.11-release"
       alwaysRecheck = true

	// Warning: We are still testing the performance of NeatFeatures plugins in combination with Apollo.
	// We advise caution if enabling these plugins with Apollo until this process is finalized.
   }
   plugins {
       WebApollo{
           included = true
       }
        NeatHTMLFeatures{
            included = true
        }
        NeatCanvasFeatures{
            included = true
        }
       RegexSequenceSearch{
           included = true
       }
       HideTrackLabels{
           included = true
       }
       NAL_CSS {
          git = "https://github.com/NAL-i5K/NAL_CSS"
          branch = "supports_1.16.5-release"
          alwaysPull = true
          alwaysRecheck = true
       }
   }
}

