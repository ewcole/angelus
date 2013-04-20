
/** Combine two data files (usually English and Latin) into one dual language
 *      version, which will be used to generate the HTML page.*/
def cli = new CliBuilder(usage: "Read two Angelus files and weave them together")
cli.p(longOpt: "primary", args: 1, required: true, "Primary file")
cli.s(longOpt: "secondary", args: 1, required: true, "Secondary file")
def opt = cli.parse(args)
assert opt
def p = new File(opt.p)
def s = new File(opt.s)
def data = [
lang: [
  p: p.name.replaceAll(/.*\./, ''), 
  s: s.name.replaceAll(/.*\./, '')
],
lines: [:]
]
println data
[p,s].each {assert it.exists()}
println "files exist"
data.lines["p"] = p.readLines().grep {it =~ /[^ ]/}
data.lines.s = s.readLines().grep {it =~ /[^ ]/}
assert data.lines.p.size() == data.lines.s.size()
// Now march through the lines and create unified file.
def l = data.lines
def sections = (0 .. data.lines.p.size()).collect {
  i ->
    def pt = l.p[i];
    def st = l.s[i];
    assert pt =~ /^([a-zA-Z]+): *(.*)/
}
