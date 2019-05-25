#!/usr/bin/env groovy

class Constants {
    static final GITHUB_COMMIT_URL = "https://github.com/RedkneeSolutionsInc/UnifiedPayments-VoMS-R3/commit"

    static final COLOR_BRIGHT_BLUE = "#30A5CD"
    static final COLOR_BRIGHT_ORANGE = "#F58A33"
    static final COLOR_BRIGHT_RED = "#D03759"
    static final COLOR_DARK_GRAY = "#444444"
    static final COLOR_LIGHT_GRAY = "#DCDCDC"
    static final COLOR_YELLOW_GREEN = "#93B541"

}

def sendSlackMesage = { color, specificMessage ->
    slackSend(channel: '#topup-ci-messages',
			message: buildMessage(specificMessage, currentBuild),
            color: color,
            failOnError: false)
}

String buildMessage(specificMessage, currentBuild) {
    def nl = '\n\r'
    "*${specificMessage}*: - <${env.JOB_URL}|${env.JOB_NAME}> <${env.BUILD_URL}|${env.BUILD_NUMBER}>" +
            "$nl$nl*Change set:*${nl}" + currentBuild.changeSets.collect {
        it.items.collect {
            commitInfo(it)
        }.join(nl)
    }.join(nl) + "$nl<${env.BUILD_URL}consoleFull|*console*>$nl"
}

def commitInfo(commit) {
    commit ? "<$Constants.GITHUB_COMMIT_URL/${commit.commitId.take(7)}|${commit.commitId.take(7)}> " +
            "*${commit.msg}*  _by ${commit.author}_" : ''
}

pipeline {
    agent any

    options {
        preserveStashes()
    }

    stages {

        stage('Build Voms Apps') {
                    stages {
                        stage('Build NodeJs') {
                            steps {		
								script {						
                                sh '''
									export JAVA_HOME=$JDK_11								  
									cd voms-parent
                                    ./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 :voms-web:npmInstall  
                                    ./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 :voms-web:npmBuild -Penv=staging
                                   '''	
								}	
                            }
                        }						
                        stage('Build Java') {						    				
                            steps {
                                sh '''
									export JAVA_HOME=$JDK_11								  								
                                    cd voms-parent && ./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 bootJar -Penv=staging
                                   '''                             
                            }
                        }						
                        stage('Test Java') {						
                            steps {
                                sh '''
									export JAVA_HOME=$JDK_11
                                    cd voms-parent && ./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 test integrationTest
                                   '''
                            }
                        }						
                        stage('Validate Java') {
                            steps {
                                sh '''
									export JAVA_HOME=$JDK_11
                                    cd voms-parent && ./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 check
                                   '''
                            }
                        }
						stage('Push Build Docker Image') {						    
						    when {
								branch 'develop'
							}						
							steps {
								script {
									docker.withTool('docker') {														
										withCredentials([file(
												credentialsId: 'service-optiva-topup-registry-json-key',
												variable: 'GCP_KEY')]) {												
												sh '''
													export JAVA_HOME=$JDK_11
													gcloud auth activate-service-account --key-file ${GCP_KEY}
													gcloud -q auth configure-docker													
													cd voms-parent
													./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 dockerTagsPush -Penv=dev
												'''
										}
									}
								}
							}
						}						
						stage('Update Cloud - Snapshot') {
						    when {
								branch 'master'
							}											
							steps {
								script {
									docker.withTool('docker') {														
										withCredentials([file(
												credentialsId: 'service-optiva-topup-registry-json-key',
												variable: 'GCP_KEY')]) {																								
												sh '''
													export JAVA_HOME=$JDK_11
													gcloud auth activate-service-account --key-file ${GCP_KEY}
													gcloud -q auth configure-docker
													gcloud config set project product-topup
													gcloud config set compute/zone us-central1-a
													gcloud container clusters get-credentials topup-voms-staging
													cd voms-parent
													./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 updateK8sImage -Penv=staging
												'''
										}
									}
								}
							}
						}
						stage('Update Cloud - Release') {
						    when {
								tag "*"
							}
							steps {
								script {
									docker.withTool('docker') {
										withCredentials([file(
												credentialsId: 'service-optiva-topup-registry-json-key',
												variable: 'GCP_KEY')]) {
											sh '''
												export JAVA_HOME=$JDK_11
												gcloud auth activate-service-account --key-file ${GCP_KEY}
												gcloud -q auth configure-docker
												cd voms-parent
												./gradlew -Dhttp.proxyHost=172.16.20.25 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=172.16.20.25 -Dhttps.proxyPort=3128 dockerTagsPush -Penv=prod -Ptag=$TAG_NAME
											'''
										}
									}
								}
							}
						}						
                    }
                    post {
                        success {

                            junit(allowEmptyResults: true, testResults: '**/build/test-results/**/*.xml')

                            jacoco(
                                    execPattern: '**/build/jacoco/*.exec',
                                    classPattern: '**/build/classes',
                                    sourcePattern: '**/src/main/java',
                                    exclusionPattern: '**/src/test*,**/src/generated*',
//                                    TODO: TPUP-118 Enable JaCoCo to fail build
//                                    http://prime.labs.optiva.com/job/TopUp/job/UnifiedPayments-VoMS-R3/job/develop/lastSuccessfulBuild/jacoco/api/json
                                    buildOverBuild: true,
                                    changeBuildStatus: true,
                                    minimumInstructionCoverage: '26.216858',
                                    minimumBranchCoverage: '10.456274',
                                    minimumComplexityCoverage: '25.714287',
                                    minimumLineCoverage: '27.551607',
                                    minimumMethodCoverage: '28.0',
                                    minimumClassCoverage: '63.893246',
                                    deltaBranchCoverage: '0',
                                    deltaClassCoverage: '0',
                                    deltaComplexityCoverage: '0',
                                    deltaInstructionCoverage: '0',
                                    deltaLineCoverage: '0',
                                    deltaMethodCoverage: '0'
                            )

                            checkstyle(pattern: '**/build/reports/checkstyle/*.xml',
                                    canRunOnFailed: true,
                                    defaultEncoding: 'UTF-8',
//                                    TODO: TOP-3323 Enable Checkstyle to fail build
//                                    failedNewAll: '0',
//                                    failedNewHigh: '0',
//                                    failedNewLow: '0',
//                                    failedNewNormal: '0',
                                    failedTotalAll: '0',
                                    failedTotalHigh: '0',
                                    failedTotalLow: '0',
//                                    failedTotalNormal: '0',
                                    usePreviousBuildAsReference: true)

                            findbugs(pattern: '**/build/reports/spotbugs/*.xml',
                                    canRunOnFailed: true,
                                    defaultEncoding: 'UTF-8',
//                                    failedNewAll: '0',
//                                    failedNewHigh: '0',
//                                    failedNewLow: '0',
//                                    failedNewNormal: '0',
                                    //TODO: keep decreasing this number
                                    failedTotalAll: '16',
                                    failedTotalHigh: '1',
                                    failedTotalNormal: '1',
                                    failedTotalLow: '14',
                                    usePreviousBuildAsReference: true)

                            pmd(canComputeNew: true,
                                    canRunOnFailed: true,
                                    defaultEncoding: 'UTF-8',
                                    pattern: '**/build/reports/pmd/*.xml',
//                                    TODO: TOP-3653 Enable PMD to fail build
//                                    http://prime.labs.optiva.com/job/TopUp/job/UnifiedPayments-VoMS-R3/job/develop/lastSuccessfulBuild/pmdResult/api/json
                                    failedTotalAll: '118',
                                    failedTotalHigh: '0',
                                    failedTotalLow: '0',
                                    failedTotalNormal: '118')
                        }
                    }
                }    
	}
	post {
        success {
            script {
                sendSlackMesage Constants.COLOR_YELLOW_GREEN, "Success."
            }
        }
        unstable {
            script {
                sendSlackMesage Constants.COLOR_BRIGHT_ORANGE, "Unstable."
            }
        }
        failure {
            script {
                sendSlackMesage Constants.COLOR_BRIGHT_RED, "Failed."
            }
        }
    }
}
