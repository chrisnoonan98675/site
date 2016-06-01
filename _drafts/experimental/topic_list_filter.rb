module TopicListFilter
  def topics(input, product_id, group, size=6)
    puts "Collecting topics for #{product_id}/#{group} for up to #{size} entries"
    candidates = input.select do |page|
      page.data['categories'] and (page.data['categories'].include? product_id) and (page.path.include? group) and not (page.path.include? 'index')
    end
    candidates[0, size]
  end
end

Liquid::Template.register_filter(TopicListFilter)
