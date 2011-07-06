require 'rake'
require 'yaml'

# Common Variables ---------------------------------------------------------------

PATH_SEP       = ":"
BASE_DIR       = File.dirname(__FILE__)
APP_NAME       = "mingda"
APP_VERSION    = "1.0.0"
APP_JAR        = "#{APP_NAME}/mingda-#{APP_VERSION}.jar"

SRC_DIR        = "src"
JAVA_SRC_DIR   = "#{SRC_DIR}"
LIB_DIR        = "lib"
BUILD_DIR      = "build"
DEST_DIR       = "dest"

COMPILE_PARAMS = %w( -g:none -1.5 -encoding UTF-8 -progress ).join(' ')
COMPILE_DEST_DIR = "#{BUILD_DIR}/app/WEB-INF/classes"

# Classpath ---------------------------------------------------------------------

LIB = YAML.load(File.open('lib/lib.yml'))

def build_classpath(classpath, *libs)
  libs.flatten!
  
  libs.each do |l|
    jar = LIB[l]['jar']

    jar = [jar] unless jar.class == Array
    
    jar.each do |j|
      path = File.join(LIB['dir'], LIB[l]['dir'], j)
      classpath << PATH_SEP << path unless classpath.include? path
    end
    
    # add requirements
    build_classpath(classpath, LIB[l]['require']) if LIB[l]['require']
  end
  
  classpath
end

def collect_app_jars(jars, *libs)
  libs.flatten!
  
  libs.each do |l|
    jar = LIB[l]['jar']

    jar = [jar] unless jar.class == Array
    
    jar.each do |j|
      path = File.join(LIB['dir'], LIB[l]['dir'], j)
      jars << path unless jars.include? path
    end
    
    # add requirements
    collect_app_jars(jars, LIB[l]['require']) if LIB[l]['require']
  end
  
  jars
end

COMPILE_CLASSPATH = build_classpath("", LIB['compile-classpath'])
JS_COMPRESSION_CLASSPATH = build_classpath("", LIB['js-compression-classpath'])


# Make directory structure ------------------------------------------------------

DIRS = [DEST_DIR, BUILD_DIR, "#{BUILD_DIR}/web", "#{BUILD_DIR}/app", "#{BUILD_DIR}/app/WEB-INF"]

desc "Make the directory structure for building"
task :make_dir_structure do
  DIRS.each { |dir| mkdir_p dir }
end

# Task clean -------------------------------------------------------------------
CLEAN = FileList[DEST_DIR, BUILD_DIR]

desc "Remove any temporary products."
task :clean do
  CLEAN.each { |fn| rm_rf fn rescue nil }
end

# Package Web files -----------------------------------------------------------

WEB_FILES = FileList[
    'web/images/**/*',
    'web/**/*.html',
    'web/**/*.htm',
    'web/**/*.css',
    'web/**/*.htc',
    'web/**/*.gif',
    'web/**/*.jpg',
    'web/**/*.jbf',
    'web/**/*.png',
    'web/**/*.swf',
    'web/**/*.zip',
    'web/favicon.ico',
    'web/crossdomain.xml'
  ].exclude('*.jsp', '*.ftl', 'web/WEB-INF')

desc "Copy static web files..."
task :copy_web_files do
  WEB_FILES.each do |f|
    cp_file f, "#{BUILD_DIR}/#{f}"
  end
end

def cp_file!(src, dest)
  if File.file? src
    mkdirs_if_not_exists dest
    cp src, dest
  end
end

def cp_file(src, dest)
  if File.file? src
    if not File.exists?(dest) or File.new(src).mtime > File.new(dest).mtime
      mkdirs_if_not_exists dest
      cp src, dest
    end
  end
end

def mkdirs_if_not_exists(file)
  non_exists_dirs = []
  dir = File.dirname(file)
  while not File.exists?(dir)
    non_exists_dirs << dir
    dir = File.dirname(dir)
  end
  if not non_exists_dirs.empty?
    non_exists_dirs.reverse!.each { |d| mkdir d }
  end
end

JS_COMPRESS_EXCLUDE_FILES = FileList[
    'web/scripts/common.js',
    'web/scripts/lightedit.js',
    'web/scripts/photo_upload.js',
    'web/scripts/tinymce/**/*.js',
    'web/**/lang*.js', 
    'web/scripts/calendar/**',
    'web/scripts/calendar/lang/**',
    'web/**/getcity.js'
  ]
  
JS_FILES = FileList[
    'web/**/*.js'
  ].exclude(
   # 'web/scripts/mt/mootools.v1.11.js',
   #'web/scripts/calendar/**'
  )

JS_RENAME_DICT = {
 # 'web/scripts/mt/mootools.v1.11.compressed.js' => 'web/scripts/mt/mootools.v1.11.js',
 # 'web/ext/ext-all-compressed.js' => 'web/ext/ext-all.js'
}

CSS_COMPRESS_EXCLUDE_FILES = FileList[
   # 'web/scripts/common.js',
   # 'web/scripts/lightedit.js',
   # 'web/scripts/photo_upload.js',
   # 'web/scripts/tinymce/**/*.js',
   # 'web/**/lang*.js', 
   # 'web/**/getcity.js'
  ]
  
CSS_FILES = FileList[
    'web/styles/*.css',
    'web/styles/calendar/**/*.css',
    'web/styles/main/*.css'
  ].exclude(
   # 'web/scripts/mt/mootools.v1.11.js',
   # 'web/ext/ext-all.js'
  )

CSS_RENAME_DICT = {
 # 'web/scripts/mt/mootools.v1.11.compressed.js' => 'web/scripts/mt/mootools.v1.11.js',
 # 'web/ext/ext-all-compressed.js' => 'web/ext/ext-all.js'
}

desc "Compress javascript source files"
task :compress_js_files do
  excludes = JS_COMPRESS_EXCLUDE_FILES.to_a
  JS_FILES.each do |js|
    if excludes.include?(js)
      if JS_RENAME_DICT[js]
        cp_file js, "#{BUILD_DIR}/#{JS_RENAME_DICT[js]}"
      else
        cp_file js, "#{BUILD_DIR}/#{js}"
      end
    else
      # use java to compress js
      if JS_RENAME_DICT[js]
      	compress_js js, JS_RENAME_DICT[js]
      else
      	compress_js js, js
      end
    end
  end
end

desc "Compress css source files"
task :compress_css_files do
  excludes = CSS_COMPRESS_EXCLUDE_FILES.to_a
  CSS_FILES.each do |css|
    if excludes.include?(css)
      if CSS_RENAME_DICT[css]
        cp_file css, "#{BUILD_DIR}/#{CSS_RENAME_DICT[css]}"
      else
        cp_file css, "#{BUILD_DIR}/#{css}"
      end
    else
      # use java to compress js
      if CSS_RENAME_DICT[css]
        compress_css css, CSS_RENAME_DICT[css]
      else
        compress_css css, css
      end
    end
  end
end

def compress_js(js_file, to_file)
  puts "compressing javascript: #{js_file}"
  #sh "java -cp #{JS_COMPRESSION_CLASSPATH} com.yupoo.tools.JSCompressor -l #{js_file} #{BUILD_DIR}/#{to_file}"
  sh "java -cp #{JS_COMPRESSION_CLASSPATH} com.yahoo.platform.yui.compressor.YUICompressor --type js --charset utf-8 #{js_file} > #{BUILD_DIR}/#{to_file}"
end

def compress_css(css_file, to_file)
  puts "compressing css: #{css_file}"
  sh "java -cp #{JS_COMPRESSION_CLASSPATH} com.yahoo.platform.yui.compressor.YUICompressor --type css --charset utf-8 #{css_file} > #{BUILD_DIR}/#{to_file}"
end

desc "Package static web files"
task :pkg_web do
  puts "Archiving static web files..."
  sh "tar -cf #{DEST_DIR}/web.tar.gz --directory=#{BUILD_DIR} web"
end
task :pkg_web => [ :make_dir_structure, :copy_web_files, :compress_js_files, :compress_css_files ]

# Package App files -----------------------------------------------------------

APP_FILES = FileList[
    'web/**/*.ftl',
    'web/**/*.jsp',
    'web/**/*.xml',
    'web/**/*.tld',
    'web/**/*.properties'
  ].exclude('web/crossdomain.xml', 'web/WEB-INF/classes')

desc "Copy application files..."
task :copy_app_files do
  APP_FILES.each do |f|
    destfile = f.sub(/^web/, "app")
    cp_file f, "#{BUILD_DIR}/#{destfile}"
  end
end
task :copy_app_files => :make_dir_structure

desc "Compile java source files use ajc"
task :compile_java  do
  puts "compile java..."
  sh "ajc -cp #{COMPILE_CLASSPATH} #{COMPILE_PARAMS} -sourceroots #{JAVA_SRC_DIR} -d #{COMPILE_DEST_DIR}"
end
task :compile_java => :make_dir_structure

desc "Copy application configuration files"
task :copy_app_conf do
  FileList['src/conf/**/*'].each do |f|
    destfile = f.sub(/^src\/conf\//, '')
    cp_file f, "#{BUILD_DIR}/app/WEB-INF/classes/#{destfile}"
  end
end

desc "Copy application configuration files"
task :copy_webapp_conf do
  FileList['src/webapp/**/*.xml', 'src/webapp/**/*.properties', 'src/webapp/**/*.ftl'].each do |f|
    destfile = f.sub(/^src\/webapp\/(conf|resources)\//, '')
    cp_file f, "#{BUILD_DIR}/app/WEB-INF/classes/#{destfile}"
  end
end

desc "Copy production environment configuration files"
task :copy_production_conf do
  FileList['metadata/webapp/**/*'].each do |f|
    destfile = f.sub(/^metadata\/webapp\//, '')
    cp_file! f, "#{BUILD_DIR}/app/#{destfile}"
  end
end

task :copy_libs do
  libs = collect_app_jars([], LIB['deploy-classpath'])
  libs.each do |jar|
    cp_file jar, "#{BUILD_DIR}/app/WEB-INF/lib/#{File.basename(jar)}"
  end
end

desc "Archive application files"
task :archive_app do
  puts "Archiving application files..."
  sh "tar cfz #{DEST_DIR}/app.tar.gz --directory=#{BUILD_DIR}/app ."
end

task :pkg_app => [ :make_dir_structure, :copy_app_files, :copy_app_conf, :copy_production_conf, :copy_libs, :compile_java, :copy_webapp_conf, :archive_app ]

task :pkg_app_nolib => [ :make_dir_structure, :copy_app_files, :copy_app_conf, :copy_production_conf, :compile_java, :copy_webapp_conf, :archive_app ]

task :pkg_lib do
  puts "Archiving application libraries..."
  sh "tar cf #{DEST_DIR}/lib.tar --directory=#{BUILD_DIR}/app/WEB-INF/lib ."
end

# Deploy application -----------------------------
# Invoke the given actions via Capistrano


def cap(*parameters)
  begin
    require 'rubygems'
  rescue LoadError
    # no rubygems to load, so we fail silently
  end

  require 'capistrano/cli'

  STDERR.puts "Capistrano/Rake integration is deprecated."
  STDERR.puts "Please invoke the 'cap' command directly: `cap #{parameters.join(" ")}'"

  Capistrano::CLI.new(parameters.map { |param| param.to_s }).execute!
end

namespace :remote do
  desc "Execute a specific action using capistrano"
  task :exec do
    unless ENV['ACTION']
      raise "Please specify an action (or comma separated list of actions) via the ACTION environment variable"
    end

    actions = ENV['ACTION'].split(",")
    actions.concat(ENV['PARAMS'].split(" ")) if ENV['PARAMS']

    cap(*actions)
  end
end