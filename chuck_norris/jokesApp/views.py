from django.shortcuts import render
from django.http import HttpResponseRedirect

from .forms import NameForm

import redis

r = redis.StrictRedis(host='localhost', port=6379, db=0)

#Django: working with forms (djangoproject)
def get_name(request):
    # if this is a POST request we need to process the form data
    if request.method == 'POST':
        # create a form instance and populate it with data from the request:
        form = NameForm(request.POST)
        # check whether it's valid:
        if form.is_valid():
            # process the data in form.cleaned_data as required
            firstname = form.cleaned_data['firstname']
            lastname = form.cleaned_data['lastname']
            url = "http://api.icndb.com/jokes/random?firstName=" + firstname + "&lastName=" + lastname
            # redirect to a new URL:
            return HttpResponseRedirect('#')

    # if a GET (or any other method) we'll create a blank form
    else:
        form = NameForm()

        return render(request, 'jokesApp/index.html', {'form': form})