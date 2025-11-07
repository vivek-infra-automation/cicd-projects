import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def env = System.getenv()
def user = env['JENKINS_USER'] ?: 'admin'
def pass = env['JENKINS_PASS'] ?: 'admin@123'

println "--> Creating Jenkins admin user: ${user}"

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)
instance.save()
println "--> Admin user created"
